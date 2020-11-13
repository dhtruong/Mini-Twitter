package miniTwitter;

import java.awt.Color; 
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TreeModel extends DefaultTreeCellRenderer {

	private JPanel model;
	private JLabel label;

	public TreeModel() {
		model = new JPanel();
		label = new JLabel();
		model.add(label);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		Component returnValue = null;

		if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
			
			Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
			
			if (userObject instanceof User) {
				User user = (User) userObject;
				label.setText(user.getUserID());
				label.setIcon(UIManager.getIcon("FileView.fileIcon"));
			} 
			else if (userObject instanceof UserGroup) {
				UserGroup userGroup = (UserGroup) userObject;
				label.setText(userGroup.getGroupID());
				label.setIcon(UIManager.getIcon("FileView.directoryIcon"));
			}
			if (selected) {
				label.setForeground(Color.BLUE);
			}
			else{
				label.setForeground(Color.BLACK);
			}
			
			model.setBackground(Color.WHITE);
			returnValue = model;
		}
		return returnValue;
	}

}