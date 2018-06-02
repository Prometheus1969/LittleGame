package snake;

import javax.swing.*;

public class Snake {
	public static void main(String[] args) {
		// ���� 900*700 �İ�ɫ����
		JFrame frame = new JFrame();
		frame.setBounds(400, 200, 900, 720);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon("snake.png").getImage());
		
		// ��ӻ���
		SnakePanel panel = new SnakePanel();
		frame.add(panel);
		
		frame.setVisible(true);
	}
}
