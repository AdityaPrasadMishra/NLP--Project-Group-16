#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 16 16:28:54 2018

@author: aditya
"""
#Importing Libraries
import numpy as np
import tensorflow as tf
import re
import time


######---------------------Part 1- Data Preprocessing---------------------#############
#Importing the Dataset
languageone =open('test.en', encoding = 'utf-8', errors = 'ignore').read().split('\n')
languagetwo =open('test.hi', encoding = 'utf-8', errors = 'ignore').read().split('\n')

def clean_text(text):
    text= text.lower()
    text = re.sub(r"i'm", "i am", text)
    text = re.sub(r"he's", "he is", text)
    text = re.sub(r"she's", "she is", text)
    text = re.sub(r"that's", "that is", text)
    text = re.sub(r"what's", "what is", text)
    text = re.sub(r"where's", "where is", text)
    text = re.sub(r"\'ll", " will", text)
    text = re.sub(r"\'ve", " have", text)
    text = re.sub(r"\'re", " are", text)
    text = re.sub(r"\'d", " would", text)
    text = re.sub(r"won't", "will not", text)
    text = re.sub(r"can't", "cannot", text)
    text = re.sub(r"[-()\"#/@;:<>{}+=~|.?\',!%*]", "", text)
    return text

# Cleaning the languageone
clean_languageone = []
for langoneline in languageone:
    clean_languageone.append(clean_text(langoneline))
 
# Cleaning the languagetwo
clean_languagetwo = []
for langtwoline in languagetwo:
    clean_languagetwo.append(clean_text(langtwoline))

# Creating a dictionary that maps each word to its number of occurrences
wordlist = {}
for langoneline in clean_languageone:
    for word in langoneline.split(' '):
        if word not in wordlist:
            if(word.strip()!=''):
                wordlist[word] = 1
        else:
            wordlist[word] += 1
wordlist2={}
for langtwoline in clean_languagetwo:
    for word in langtwoline.split(' '):
        if word not in wordlist2:
            if(word.strip()!=''):
                wordlist2[word] = 1
        else:
            wordlist2[word] += 1

# Creating two dictionaries that map the languageoneword and thelanguagetwo words to a unique integer
threshold = 20
languageonewordstoint ={}
word_number =0
for word,count in wordlist.items():
    if count >= threshold:
        languageonewordstoint[word] = word_number
        word_number += 1

languagetwowordstoint ={}
word_number =0
for word,count in wordlist2.items():
    if count >= threshold:
        languagetwowordstoint[word] = word_number
        word_number += 1
#        if(len(languagetwowordstoint) == len(languageonewordstoint)):
#            break;
                
# Adding the last tokens to these two dictionaries
tokens = ['<PAD>', '<EOS>', '<OUT>', '<SOS>']
for token in tokens:
    languageonewordstoint[token] = len(languageonewordstoint) + 1
for token in tokens:
    languagetwowordstoint[token] = len(languagetwowordstoint) + 1
 
# Creating the inverse dictionary of the languagetwowordstoint dictionary
languagetwoints2word = {w_i: w for w, w_i in languagetwowordstoint.items()}
 
# Adding the End Of String token to the end of every langtwo
for i in range(len(clean_languagetwo)):
    clean_languageone[i] += ' <EOS>'
for i in range(len(clean_languagetwo)):
    clean_languagetwo[i] += ' <EOS>'
 
# Translating all the languageone and the languagetwo into integers
# and Replacing all the words that were filtered out by <OUT> 
languageone_into_int = []
for langone in clean_languageone:
    ints = []
    for word in langone.split(' '):
        if word not in languageonewordstoint:
            ints.append(languageonewordstoint['<OUT>'])
        else:
            ints.append(languageonewordstoint[word])
    languageone_into_int.append(ints)
languagetwo_into_int = []
for langtwo in clean_languagetwo:
    ints = []
    for word in langtwo.split(' '):
        if word not in languagetwowordstoint:
            ints.append(languagetwowordstoint['<OUT>'])
        else:
            ints.append(languagetwowordstoint[word])
    languagetwo_into_int.append(ints)
 
# Sorting languageone and languagetwo by the length of languageone
sorted_clean_languageone = []
sorted_clean_languagetwo = []
for length in range(1, 30 + 1):
    for i in enumerate(languageone_into_int):
        if len(i[1]) == length:
            sorted_clean_languageone.append(languageone_into_int[i[0]])
            sorted_clean_languagetwo.append(languagetwo_into_int[i[0]])


# Creating placeholders for the inputs and the targets
def createsampleinput():
    inputs = tf.placeholder(tf.int32, [None, None], name = 'input')
    targets = tf.placeholder(tf.int32, [None, None], name = 'target')
    lr = tf.placeholder(tf.float32, name = 'learning_rate')
    keep_prob = tf.placeholder(tf.float32, name = 'keep_prob')
    return inputs, targets, lr, keep_prob
 
# Preprocessing the targets
def preprocessfortfmodel(targets, word2int, batch_size):
    left_side = tf.fill([batch_size, 1], word2int['<SOS>'])
    right_side = tf.strided_slice(targets, [0,0], [batch_size, -1], [1,1])
    preprocessed_targets = tf.concat([left_side, right_side], 1)
    return preprocessed_targets
 
# Creating the Encoder RNN
def encoder(rnn_inputs, rnn_size, num_layers, keep_prob, sequence_length):
    lstm = tf.contrib.rnn.BasicLSTMCell(rnn_size)
    lstm_dropout = tf.contrib.rnn.DropoutWrapper(lstm, input_keep_prob = keep_prob)
    encoder_cell = tf.contrib.rnn.MultiRNNCell([lstm_dropout] * num_layers)
    encoder_output, encoder_state = tf.nn.bidirectional_dynamic_rnn(cell_fw = encoder_cell,
                                                                    cell_bw = encoder_cell,
                                                                    sequence_length = sequence_length,
                                                                    inputs = rnn_inputs,
                                                                    dtype = tf.float32)
    return encoder_state
 
# Decoding the training set
def decode_training_set(encoder_state, decoder_cell, decoder_embedded_input, sequence_length, decoding_scope, output_function, keep_prob, batch_size):
    attention_states = tf.zeros([batch_size, 1, decoder_cell.output_size])
    attention_keys, attention_values, attention_score_function, attention_construct_function = tf.contrib.seq2seq.prepare_attention(attention_states, attention_option = "bahdanau", num_units = decoder_cell.output_size)
    training_decoder_function = tf.contrib.seq2seq.attention_decoder_fn_train(encoder_state[0],
                                                                              attention_keys,
                                                                              attention_values,
                                                                              attention_score_function,
                                                                              attention_construct_function,
                                                                              name = "attn_dec_train")
    decoder_output, decoder_final_state, decoder_final_context_state = tf.contrib.seq2seq.dynamic_rnn_decoder(decoder_cell,
                                                                                                              training_decoder_function,
                                                                                                              decoder_embedded_input,
                                                                                                              sequence_length,
                                                                                                              scope = decoding_scope)
    decoder_output_dropout = tf.nn.dropout(decoder_output, keep_prob)
    return output_function(decoder_output_dropout)
 
# Decoding the test/validation set
def decode_test_set(encoder_state, decoder_cell, decoder_embeddings_matrix, sos_id, eos_id, maximum_length, num_words, decoding_scope, output_function, keep_prob, batch_size):
    attention_states = tf.zeros([batch_size, 1, decoder_cell.output_size])
    attention_keys, attention_values, attention_score_function, attention_construct_function = tf.contrib.seq2seq.prepare_attention(attention_states, attention_option = "bahdanau", num_units = decoder_cell.output_size)
    test_decoder_function = tf.contrib.seq2seq.attention_decoder_fn_inference(output_function,
                                                                              encoder_state[0],
                                                                              attention_keys,
                                                                              attention_values,
                                                                              attention_score_function,
                                                                              attention_construct_function,
                                                                              decoder_embeddings_matrix,
                                                                              sos_id,
                                                                              eos_id,
                                                                              maximum_length,
                                                                              num_words,
                                                                              name = "attn_dec_inf")
    test_predictions, decoder_final_state, decoder_final_context_state = tf.contrib.seq2seq.dynamic_rnn_decoder(decoder_cell,
                                                                                                                test_decoder_function,
                                                                                                                scope = decoding_scope)
    return test_predictions
 
# Creating the Decoder RNN
def decoder(decoder_embedded_input, decoder_embeddings_matrix, encoder_state, num_words, sequence_length, rnn_size, num_layers, word2int, keep_prob, batch_size):
    with tf.variable_scope("decoding") as decoding_scope:
        lstm = tf.contrib.rnn.BasicLSTMCell(rnn_size)
        lstm_dropout = tf.contrib.rnn.DropoutWrapper(lstm, input_keep_prob = keep_prob)
        decoder_cell = tf.contrib.rnn.MultiRNNCell([lstm_dropout] * num_layers)
        weights = tf.truncated_normal_initializer(stddev = 0.1)
        biases = tf.zeros_initializer()
        output_function = lambda x: tf.contrib.layers.fully_connected(x,
                                                                      num_words,
                                                                      None,
                                                                      scope = decoding_scope,
                                                                      weights_initializer = weights,
                                                                      biases_initializer = biases)
        training_predictions = decode_training_set(encoder_state,
                                                   decoder_cell,
                                                   decoder_embedded_input,
                                                   sequence_length,
                                                   decoding_scope,
                                                   output_function,
                                                   keep_prob,
                                                   batch_size)
        decoding_scope.reuse_variables()
        test_predictions = decode_test_set(encoder_state,
                                           decoder_cell,
                                           decoder_embeddings_matrix,
                                           word2int['<SOS>'],
                                           word2int['<EOS>'],
                                           sequence_length - 1,
                                           num_words,
                                           decoding_scope,
                                           output_function,
                                           keep_prob,
                                           batch_size)
    return training_predictions, test_predictions
 
# Building the seq2seq model
def seq2seq_model(inputs, targets, keep_prob, batch_size, sequence_length, languagetwo_num_words, languageone_num_words, encoder_embedding_size, decoder_embedding_size, rnn_size, num_layers, languagetwowordstoint):
    encoder_embedded_input = tf.contrib.layers.embed_sequence(inputs,
                                                              languageone_num_words + 1,
                                                              encoder_embedding_size,
                                                              initializer = tf.random_uniform_initializer(0, 1))
    encoder_state = encoder(encoder_embedded_input, rnn_size, num_layers, keep_prob, sequence_length)
    preprocessed_targets = preprocessfortfmodel(targets, languageonewordstoint, batch_size)
    decoder_embeddings_matrix = tf.Variable(tf.random_uniform([languagetwo_num_words + 1, decoder_embedding_size], 0, 1))
    decoder_embedded_input = tf.nn.embedding_lookup(decoder_embeddings_matrix, preprocessed_targets)
    training_predictions, test_predictions = decoder(decoder_embedded_input,
                                                         decoder_embeddings_matrix,
                                                         encoder_state,
                                                         languagetwo_num_words,
                                                         sequence_length,
                                                         rnn_size,
                                                         num_layers,
                                                         languagetwowordstoint,
                                                         keep_prob,
                                                         batch_size)
    return training_predictions, test_predictions

##################################Part 3 - training the SEQ2SEQ Model #################################
# setting the Hyperparameter
epochs = 20
batch_size = 64
rnn_size = 256
num_layers = 3
encoding_embedding_size = 256
decoding_embedding_size = 256
learning_rate =0.01
learning_rate_decay = 0.9
min_learning_rate = 0.0001
keep_probability = 0.5

#Defining a Session
tf.reset_default_graph()
sess = tf.InteractiveSession()
#Loading the modelinouts
inputs,targets,lr,keep_prob =createsampleinput()

#Setting the Sequence length
sequence_length = tf.placeholder_with_default(25,None,name='sequence_length')

#Getting the shape of Input tensor
input_shape = tf.shape(inputs)

#Getting the Training and Test Predictions
training_predictions, test_predictions = seq2seq_model(tf.reverse(inputs,[-1]),
                                                                targets,
                                                                keep_prob,
                                                                batch_size,
                                                                sequence_length,
                                                                len(languagetwowordstoint),
                                                                len(languageonewordstoint),
                                                                encoding_embedding_size,
                                                                decoding_embedding_size,
                                                                rnn_size,
                                                                num_layers,
                                                               languagetwowordstoint )

with tf.name_scope("Optimization"):
    loss_error = tf.contrib.seq2seq.sequence_loss(training_predictions,
                                                  targets, 
                                                  tf.ones([input_shape[0],sequence_length]))
    optimizer = tf.train.AdamOptimizer(learning_rate)
    gradients = optimizer.compute_gradients(loss_error)
    clipped_gradients =[(tf.clip_by_value(grad_tensor,-5.,5.),grad_variable)for grad_tensor, grad_variable in gradients if grad_tensor is not None]
    optimizer_gradient_clipping = optimizer.apply_gradients(clipped_gradients)

#padding the Sequences with <PAD> token
def apply_padding(batch_of_seq, word2int):
    max_seq_len =max([len(seq)for seq in batch_of_seq])
    return [seq + [word2int['<PAD>']]*(max_seq_len-len(seq))for seq in batch_of_seq]

# Spliting the data into batches of languageone and languagetwo
def split_into_batches(languageone,languagetwo,batch_size):
    for batch_index in range(0,len(languageone)//batch_size):
        start_index = batch_index*batch_size
        languageone_in_batch = languageone[start_index:start_index+batch_size]
        languagetwo_in_batch =languagetwo[start_index:start_index+batch_size]
        padded_languageone_in_batch =np.array(apply_padding(languageone_in_batch,languageonewordstoint))
        padded_languagetwo_in_batch =np.array(apply_padding(languagetwo_in_batch,languagetwowordstoint))
        yield padded_languageone_in_batch,padded_languagetwo_in_batch

# Splitting the languageone and languagetwo into training and validation sets
training_validation_split = int(len(sorted_clean_languageone)*0.15)
test_validation_split = int(len(sorted_clean_languagetwo)*0.15)
training_languageone = sorted_clean_languageone[training_validation_split:]
training_languagetwo = sorted_clean_languagetwo[test_validation_split:]
validation_languageone = sorted_clean_languageone[:training_validation_split]
validation_languagetwo = sorted_clean_languagetwo[:test_validation_split]

#training_validation_split = len(languageone_into_int)
#test_validation_split = len(languagetwo_into_int)
#training_languageone = languageone_into_int
#training_languagetwo = languagetwo_into_int
#validation_languageone = languageone_into_int
#validation_languagetwo = languagetwo_into_int






########## PART 4 - TESTING THE SEQ2SEQ MODEL ##########
 
 
 
# Loading the weights and Running the session
checkpoint = "./SemanticTranslation_weights.ckpt"
session = tf.InteractiveSession()
session.run(tf.global_variables_initializer())
saver = tf.train.Saver()
saver.restore(session, checkpoint)
 
# Converting the languageone from strings to lists of encoding integers
def convert_string2int(langoneline, word2int):
    langoneline = clean_text(langoneline)
    return [word2int.get(word, word2int['<OUT>']) for word in langoneline.split()]
 
# Setting up the chat
while(True):
    langoneline = input("You: ")
    if langoneline == 'Goodbye':
        break
    langoneline = convert_string2int(langoneline, languageonewordstoint)
    langoneline = langoneline + [languageonewordstoint['<PAD>']] * (20 - len(langoneline))
    print(langoneline)
    fake_batch = np.zeros((batch_size, 20))
    fake_batch[0] = langoneline
    predicted_langtwoline = session.run(test_predictions, {inputs: fake_batch, keep_prob: 0.5})[0]
 #   print(predicted_langtwoline)
    langtwoline = ''
    for i in np.argmax(predicted_langtwoline, 1):
        print (i)
        if languagetwoints2word[i] == '<EOS>':
            token = '|'
        elif languagetwoints2word[i] == '<OUT>':
            token = 'out '
        elif languagetwoints2word[i] == '<PAD>':
            token = 'pad '
        else:
            token = ' ' + languagetwoints2word[i]
        langtwoline += token
        if token == '|':
            break
    print('MAx Val: ' + langtwoline)
    for j in range(len(predicted_langtwoline[1])):
      for i in predicted_langtwoline[1][j]:
        if languagetwoints2word[i] == '<EOS>':
            token = '|'
        elif languagetwoints2word[i] == '<OUT>':
            token = 'out '
        elif languagetwoints2word[i] == '<PAD>':
            token = 'pad '
        else:
            token = ' ' + languagetwoints2word[i]
        langtwoline += token
        if token == '|':
            break
      print('other Val: ' + langtwoline)