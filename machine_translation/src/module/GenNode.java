package module;

import java.util.ArrayList;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.Setter;

public class GenNode {
	@Setter (AccessLevel.PUBLIC) @Getter (AccessLevel.PUBLIC) private String label = null;
	@Setter (AccessLevel.PUBLIC) @Getter (AccessLevel.PUBLIC) private String semanticRole = null;
	@Setter (AccessLevel.PUBLIC) @Getter (AccessLevel.PUBLIC) private int id = -1;
	@Setter (AccessLevel.PUBLIC) @Getter (AccessLevel.PUBLIC) private ArrayList<GenNode> children = new ArrayList<GenNode>();
}






