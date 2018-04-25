#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Apr 24 22:03:42 2018

@author: aditya
"""
import codecs
import numpy as np
import matplotlib.pyplot as plt
import tensorflow as tf
import re
import time
import random

training_errors= [2.880890389084816, 2.589109110832214, 2.529299919307232, 2.4656221523880957, 2.403166337311268, 2.4464233142137526, 2.375447287261486, 2.3676583436131478, 2.3073809832334518, 2.359292232692242, 2.3350265485048296, 2.2823567962646485, 2.2687944075465203, 2.2951272314786912, 2.282493242323399, 2.2626508274674415, 2.258985004425049, 2.2431524062156676, 2.226141297519207, 2.2092211750149726, 2.215476005077362, 2.209359174370766, 2.1948138630390166, 2.193422330915928, 2.1807888588309288]
validation_errors=[2.435073028564453, 2.3433548278808596, 2.315739807128906, 2.2985709838867185, 2.2562687683105467, 2.2732557678222656, 2.2361006469726563, 2.222836364746094, 2.2145645141601564, 2.2109912719726563, 2.20832763671875, 2.1882525634765626, 2.1855752868652343, 2.183926452636719, 2.180730743408203, 2.1761441955566405, 2.1777948303222656, 2.1717729797363283, 2.1632617797851563, 2.161899658203125, 2.16302978515625, 2.155210754394531, 2.147050506591797, 2.1581705627441408, 2.1517400207519533]
epochnum=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25]

fig1 = plt.figure()
axes1 = fig1.add_axes([0.1,0.1,0.8,0.8])
axes1.plot(epochnum,training_errors,'r')
axes1.set_xlabel('Train Epochs')
axes1.set_ylabel('Training Error')
fig1.savefig('trainAccuracy.png')

fig2 = plt.figure()
axes2 = fig2.add_axes([0.1,0.1,0.8,0.8])
axes2.plot(epochnum,validation_errors,'b')
axes2.set_xlabel('Train Epochs')
axes2.set_ylabel('Validation Error')
fig2.savefig('valAccuracy.png')
plt.plot()
