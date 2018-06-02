package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	// ������ͷͼƬ
	ImageIcon up = new ImageIcon("up.jpg");
	ImageIcon down = new ImageIcon("down.jpg");
	ImageIcon left = new ImageIcon("left.jpg");
	ImageIcon right = new ImageIcon("right.jpg");
	// ��������ͼƬ
	ImageIcon title = new ImageIcon("title.png");
	ImageIcon food = new ImageIcon("food.png");
	ImageIcon body = new ImageIcon("body.png");

	
	// �ߵĽṹ���
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	int len = 3;
	String direction = "R";   // R�� L�� U�� D��
	
	// ʳ�������
	Random r = new Random();  // �������λ��
	int foodx = r.nextInt(34)*25 + 25;
	int foody = r.nextInt(24)*25 + 75;
	
	// ��Ϸ�Ƿ�ʼ�ı�־
	boolean isStarted = false;
	
	// ��Ϸ�Ƿ�ʧ�ܵı�־
	boolean isFaild = false;
	
	// �����ٶȺ����ѵȼ�
	int speed = 350;
	int level = 1;
	
	// ���ö�ʱ����ʱ�� ����actionPerformed()�ķ���
	Timer timer = new Timer(speed, this);
	// Timer(delay, listener)
	// delay �����ִ�� listener
	
	// ����ͳ��
	int score = 0;
	
	public SnakePanel() {
		setFocusable(true);   // ��ȡ������в���
		initSnake();          // ���þ�̬��ʼ������ 
		addKeyListener(this); // ��Ӽ����¼��ı���ͷ�����״̬
							  // ����keyPressed
		timer.start();		  // ��ʱ����ʼ
							  // ����actionPerformed
	}
	
	// ��ʼ����
	public void initSnake() {
		repaint();
		// ���ݳ�ʼ��
		speed = 350;		  // �ٶȳ�ʼ�� 
		isStarted = false;	  // ״̬�жϳ�ʼ��
		isFaild = false;	  // ʧ���жϳ�ʼ��
		score = 0;			  // �÷ֳ�ʼ��
		level = 1;			  // ���ѵȼ���ʼ��
		len = 3;			  // �ߵĳ��ȳ�ʼ��
		direction = "R";	  // ��ͷ�����ʼ��
		snakex[0] = 100;      // ͷ������λ�ó�ʼ��
		snakey[0] = 100;
		snakex[1] = 75;		  // �����һ�ڵ������ʼ��
		snakey[1] = 100;
		snakex[2] = 50;		  // ����ڶ��ڵ������ʼ��
		snakey[2] = 100;
	}
	
	public void paint(Graphics g) {
		repaint();
		
		// ������ʼ��  ������һ����Ϸ��ͼƬ���ºۼ�
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 900, 720);
		
		// ������Ϸ����ı�����ɫ
		g.setColor(Color.BLACK);
		
		// x�ĳ�Ϊ850( 34������ * 25���ӱ߳� ) y�ĳ�Ϊ600( 24������ * 25���ӱ߳� )
		g.fillRect(25, 75, 850, 600);
		
		// ���ñ����
		title.paintIcon(this, g, 25, 11);
		
		// ������ͷ(����λ�úͷ���)
		if( direction.equals("R")) {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if( direction.equals("L") ) {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if( direction.equals("U") ) {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}
		else if( direction.equals("D") ) {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		
		// ���������ÿһ�ڵ�λ��
		for( int i=1; i<len; i++ ) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}	
		
		// ����ʳ��
		food.paintIcon(this, g, foodx, foody);
		
		// ����(�ߵĳ���)ͳ�Ƶ���ʾ
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 17));
		g.drawString("Score: "+ score, 750, 30);
		g.drawString("Lengh: "+ len, 750, 50);
		
		// ���ѵȼ�����ʾ
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		g.drawString("Level: "+ level, 30, 30);
		
		// ������ʼ��ʾ��
		if( !isStarted ) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press Space to Start/Pause!", 245, 350);
		}
		
		// ����ʧ����ʾ��
		if( isFaild ) {
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Game Over && Press Space to Start!", 195, 350);
		}
	}
	
	// �����¼�  �ı���ͷ�����Լ���Ϸ״̬
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if( keyCode == KeyEvent.VK_SPACE ) {
			if( isFaild ) {
				initSnake();			 // ��Ϸʧ��  ���¿�ʼ
			}
			else{
				isStarted = !isStarted;  // �л���Ϸ״̬  ����/��ͣ 
			}
			repaint();
		}
		
		// ��ͷ����ĸı�
		else if( keyCode == KeyEvent.VK_UP && !direction.equals("D")) {
			direction = "U";
		}
		else if( keyCode == KeyEvent.VK_DOWN && !direction.equals("U")) {
			direction = "D";
		}
		else if( keyCode == KeyEvent.VK_LEFT && !direction.equals("R")) {
			direction = "L";
		}
		else if( keyCode == KeyEvent.VK_RIGHT && !direction.equals("L")) {
			direction = "R";
		}
	}
	
	// �����趨����  �߽����ƶ�
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		// ֻ�������Դ���Ҳ���ͣ�������ǰ��  
		if( isStarted && !isFaild ) {
			// �ƶ��ߵ�����  ÿһ�ڵ������궼��ǰһ�ڵľ����� ��ͷ��������ݳ�����мӼ�
			for( int i=len; i>0; i-- ) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if( direction.equals("R")) {
				// ��ͷ������   + 25
				snakex[0] = snakex[0] + 25;
				if( snakex[0] > 850 ) {
					isFaild = true;       // ��Ϸ�������ж�  ����ͷ�뿪��Ϸ����
				}
			}
			else if( direction.equals("L")) {
				// ��ͷ������   - 25
				snakex[0] = snakex[0] - 25;
				if( snakex[0] < 25 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("U")) {
				// ��ͷ������   - 25
				snakey[0] = snakey[0] - 25;
				if( snakey[0] < 75 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("D")) {
				// ��ͷ������   + 25
				snakey[0] = snakey[0] + 25;
				if( snakey[0] > 650 ) {
					isFaild = true;
				}
			}
			// ��ʳ����ж� �Ե�ʳ���ʳ���������ʧ������ �߱䳤
			if( snakex[0] == foodx && snakey[0] == foody ) 
			{
				len++;
				score++;
				foodx = r.nextInt(34)*25 + 25;
				foody = r.nextInt(24)*25 + 75;
				
				// ����ߵ��ٶ� �����Ѷ�
				if( score >= 1 ) {
					speed = 300;
					level = 2;
				}
				else if( score >= 3 ) {
					speed = 250;
					level = 3;
				}
				else if( score >= 5 ) {
					speed = 200;
					level = 4;
				}
				else if( score >= 10 ) {
					speed = 175;
					level = 5;
				}
				else if( score >= 15 ) {
					speed = 150;
					level = 6;
				}
				else if( score >= 25 ) {
					speed = 130;
					level = 7;
				}
				else if( score >= 35 ) {
					speed = 115;
					level = 8;
				}
				else if( score >= 50 ) {
					speed = 100;
					level = 9;
				}
				else if( score >= 100) {
					speed = 80;
					level = 10;
				}
				else if( score >= 200){
					speed = 60;
					level = 11;
				}
				repaint();
			}
			// ��Ϸʧ�ܵ��ж� ��ͷ������Ӵ�
			for( int i=1; i<len; i++ ) {
				if( snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
					isFaild = true;
				}
			}
		}
		// ���»���ͼ��
		repaint();
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
}