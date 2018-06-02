package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.Random;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	// 加载蛇头图片
	ImageIcon up = new ImageIcon("up.jpg");
	ImageIcon down = new ImageIcon("down.jpg");
	ImageIcon left = new ImageIcon("left.jpg");
	ImageIcon right = new ImageIcon("right.jpg");
	// 加载其他图片
	ImageIcon title = new ImageIcon("title.png");
	ImageIcon food = new ImageIcon("food.png");
	ImageIcon body = new ImageIcon("body.png");

	
	// 蛇的结构设计
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	int len = 3;
	String direction = "R";   // R右 L左 U上 D下
	
	// 食物的数据
	Random r = new Random();  // 随机产生位置
	int foodx = r.nextInt(34)*25 + 25;
	int foody = r.nextInt(24)*25 + 75;
	
	// 游戏是否开始的标志
	boolean isStarted = false;
	
	// 游戏是否失败的标志
	boolean isFaild = false;
	
	// 设置速度和困难等级
	int speed = 350;
	int level = 1;
	
	// 设置定时器的时长 进行actionPerformed()的方法
	Timer timer = new Timer(speed, this);
	// Timer(delay, listener)
	// delay 毫秒后执行 listener
	
	// 分数统计
	int score = 0;
	
	public SnakePanel() {
		setFocusable(true);   // 获取焦点进行操作
		initSnake();          // 放置静态初始化的蛇 
		addKeyListener(this); // 添加键盘事件改变蛇头方向和状态
							  // 进行keyPressed
		timer.start();		  // 定时器开始
							  // 进行actionPerformed
	}
	
	// 初始化蛇
	public void initSnake() {
		repaint();
		// 数据初始化
		speed = 350;		  // 速度初始化 
		isStarted = false;	  // 状态判断初始化
		isFaild = false;	  // 失败判断初始化
		score = 0;			  // 得分初始化
		level = 1;			  // 困难等级初始化
		len = 3;			  // 蛇的长度初始化
		direction = "R";	  // 蛇头方向初始化
		snakex[0] = 100;      // 头的坐标位置初始化
		snakey[0] = 100;
		snakex[1] = 75;		  // 身体第一节的坐标初始化
		snakey[1] = 100;
		snakex[2] = 50;		  // 身体第二节的坐标初始化
		snakey[2] = 100;
	}
	
	public void paint(Graphics g) {
		repaint();
		
		// 画布初始化  避免上一局游戏的图片留下痕迹
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 900, 720);
		
		// 设置游戏区域的背景颜色
		g.setColor(Color.BLACK);
		
		// x的长为850( 34个格子 * 25格子边长 ) y的长为600( 24个格子 * 25格子边长 )
		g.fillRect(25, 75, 850, 600);
		
		// 设置标题框
		title.paintIcon(this, g, 25, 11);
		
		// 画出蛇头(根据位置和方向)
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
		
		// 画出蛇身的每一节的位置
		for( int i=1; i<len; i++ ) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}	
		
		// 画出食物
		food.paintIcon(this, g, foodx, foody);
		
		// 分数(蛇的长度)统计的提示
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 17));
		g.drawString("Score: "+ score, 750, 30);
		g.drawString("Lengh: "+ len, 750, 50);
		
		// 困难等级的提示
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 20));
		g.drawString("Level: "+ level, 30, 30);
		
		// 画出开始提示语
		if( !isStarted ) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press Space to Start/Pause!", 245, 350);
		}
		
		// 画出失败提示语
		if( isFaild ) {
			g.setColor(Color.red);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Game Over && Press Space to Start!", 195, 350);
		}
	}
	
	// 键盘事件  改变蛇头方向以及游戏状态
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if( keyCode == KeyEvent.VK_SPACE ) {
			if( isFaild ) {
				initSnake();			 // 游戏失败  重新开始
			}
			else{
				isStarted = !isStarted;  // 切换游戏状态  继续/暂停 
			}
			repaint();
		}
		
		// 蛇头方向的改变
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
	
	// 重新设定闹钟  蛇进行移动
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		// 只有在蛇仍存活且不暂停的情况下前进  
		if( isStarted && !isFaild ) {
			// 移动蛇的身体  每一节的新坐标都是前一节的旧坐标 蛇头的坐标根据朝向进行加减
			for( int i=len; i>0; i-- ) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if( direction.equals("R")) {
				// 蛇头横坐标   + 25
				snakex[0] = snakex[0] + 25;
				if( snakex[0] > 850 ) {
					isFaild = true;       // 游戏结束的判断  即蛇头离开游戏区域
				}
			}
			else if( direction.equals("L")) {
				// 蛇头横坐标   - 25
				snakex[0] = snakex[0] - 25;
				if( snakex[0] < 25 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("U")) {
				// 蛇头纵坐标   - 25
				snakey[0] = snakey[0] - 25;
				if( snakey[0] < 75 ) {
					isFaild = true;
				}
			}
			else if( direction.equals("D")) {
				// 蛇头横坐标   + 25
				snakey[0] = snakey[0] + 25;
				if( snakey[0] > 650 ) {
					isFaild = true;
				}
			}
			// 吃食物的判断 吃到食物后食物的坐标消失并更新 蛇变长
			if( snakex[0] == foodx && snakey[0] == foody ) 
			{
				len++;
				score++;
				foodx = r.nextInt(34)*25 + 25;
				foody = r.nextInt(24)*25 + 75;
				
				// 变更蛇的速度 增加难度
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
			// 游戏失败的判断 即头与身体接触
			for( int i=1; i<len; i++ ) {
				if( snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
					isFaild = true;
				}
			}
		}
		// 重新绘制图像
		repaint();
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
}