package snake;

import javax.swing.*;

public class Snake {
	public static void main(String[] args) {
		// 画出 900*700 的白色窗口
		JFrame frame = new JFrame();
		frame.setBounds(400, 200, 900, 720);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon("snake.png").getImage());
		
		// 添加画布
		SnakePanel panel = new SnakePanel();
		frame.add(panel);
		
		frame.setVisible(true);
	}
}
