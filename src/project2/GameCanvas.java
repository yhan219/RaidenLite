package project2;

/*
 * 
 * This program's text file encoding is UTF-8!
 *
 * 所有游戏图片均为原创
 * 
 */
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameCanvas extends JPanel implements KeyListener {
	private int difficulty = 45;
	private long rate = 1;
	private Hero hero = new Hero(180, 500);
	private Background bg = new Background(0, 0);
	private int blood_length = 100;
	private int skill_length = 100;
	private int boss_blood;
	private int score = 0;
	private boolean bossApear, skillApear;
	private boolean music = true;
	private int gift_num = 0;
	private int bossNum = 0;

	private static AudioClip backgroud = Music.backgroundMusic;
	private static AudioClip boss1die = Music.bossdieMusic;
	private static AudioClip boss2die = Music.bossdie2Music;
//	private static AudioClip boss3die = Music.bossdie3Music;
	private static AudioClip enemydie = Music.enemydieMusic;
	private static AudioClip getgift = Music.getgiftMusic;
	private static AudioClip gethurt = Music.gethurtMusic;
	private static AudioClip herodie = Music.herodeathMusic;
	private static AudioClip herobullet = Music.herobulletMusic;
	private static AudioClip skill = Music.skillMusic;
	private static AudioClip success = Music.successMusic;
	private Box box;

	private JButton startBtn = new JButton(/* "开始游戏" */);
	private JButton difficultyBtn = new JButton("难度(简单)");
	private JButton musicBtn = new JButton("音效(开启)");
	private JButton exitBtn = new JButton(/* "退出" */);

	private Animate thread;

	private List<Boss> boss_list = new ArrayList<Boss>();
	private List<Boss> die_boss = new ArrayList<Boss>();
	private List<Gift> gift_list = new ArrayList<Gift>();
	private List<Gift> die_gitf = new ArrayList<Gift>();
	private List<Skill> skill_list = new ArrayList<Skill>();
	private List<Skill> die_skill = new ArrayList<Skill>();
	private List<Enemy> enemy_list = new ArrayList<Enemy>();
	private List<Enemy> die_enemy = new ArrayList<Enemy>();
	private List<Bullet> bullet_list = new ArrayList<Bullet>();
	private List<Bullet> die_bullet = new ArrayList<Bullet>();
	private List<Explosion> explosion_list = new ArrayList<Explosion>();
	private List<Explosion> die_explosion = new ArrayList<Explosion>();
	private boolean playing;

	private boolean key_up, key_down, key_left, key_right, key_space;

	private class Animate extends Thread {
		public void run() {
			while (playing) {
				keyAction();
				objectMove();
				collision();
				addBullet();
				outRange();
				remove();
				repaint();
				rate++;
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void startGame() {
		thread = new Animate();
		enemy_list.clear();
		skill_list.clear();
		gift_list.clear();
		boss_list.clear();
		bullet_list.clear();
		startBtn.setVisible(false);
		difficultyBtn.setVisible(false);
		musicBtn.setVisible(false);
		exitBtn.setVisible(false);
		difficulty = 45;
		rate = 1;
		hero = new Hero(180, 500);
		bg = new Background(0, 0);
		blood_length = 100;
		skill_length = 100;
		score = 0;
		bossApear = false;
		skillApear = false;
		gift_num = 0;
		bossNum = 0;
		playing = true;
		thread.start();
	}

	public GameCanvas() {
		backgroud.loop();
		this.addKeyListener(this);

		startBtn.setContentAreaFilled(false);

		Font f = new Font("Serif", Font.BOLD, 20);// 设置字体及字体大小
		difficultyBtn.setFont(f);
		musicBtn.setFont(f);
		ImageIcon start_icon = new ImageIcon(Resources.startPNG);
		startBtn.setIcon(start_icon);
		ImageIcon exit_icon = new ImageIcon(Resources.exitPNG);
		exitBtn.setIcon(exit_icon);

		difficultyBtn.setForeground(Color.BLUE);
		musicBtn.setForeground(Color.GREEN);
		difficultyBtn.setContentAreaFilled(false);
		musicBtn.setContentAreaFilled(false);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setContentAreaFilled(false);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
				backgroud.stop();
				success.stop();
			}
		});
		difficultyBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (difficultyBtn.getText().equals("难度(简单)")) {
					difficultyBtn.setText("难度(一般)");
					difficulty = 45;
				} else if (difficultyBtn.getText().equals("难度(一般)")) {
					difficultyBtn.setText("难度(困难)");
					difficulty = 25;
				} else if (difficultyBtn.getText().equals("难度(困难)")) {
					difficultyBtn.setText("难度(简单)");
					difficulty = 15;
				}
			}
		});
		musicBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (musicBtn.getText().equals("音效(开启)")) {
					musicBtn.setText("音效(关闭)");
					music = false;
					backgroud.stop();
				} else if (musicBtn.getText().equals("音效(关闭)")) {
					musicBtn.setText("音效(开启)");
					backgroud.loop();
					music = true;
				}
			}
		});

		box=Box.createVerticalBox();
		box.add(Box.createHorizontalStrut(150));
		box.add(startBtn);
		box.add(Box.createVerticalStrut(20));
		box.add(difficultyBtn);
		box.add(Box.createVerticalStrut(20));
		box.add(musicBtn);
		box.add(Box.createVerticalStrut(20));
		box.add(exitBtn);
		box.add(Box.createVerticalStrut(250));
		this.setLayout(new BorderLayout());
		this.add(box,BorderLayout.CENTER);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		bg.draw(g);
		for (Enemy e : enemy_list)
			e.draw(g);
		for (Bullet b : bullet_list)
			b.draw(g);
		for (Skill s : skill_list)
			s.draw(g);
		for (Boss b : boss_list)
			b.draw(g);
		for (Explosion e : explosion_list)
			e.draw(g);
		for (Gift gf : gift_list)
			gf.draw(g);
		hero.draw(g);

		Graphics2D g1 = (Graphics2D) g;
		g1.setColor(Color.RED);
		g1.drawString("血量：", 10, 20);
		g1.drawRect(50, 10, 100, 10);
		if (blood_length <= 40) {// 设置血量低于40闪烁
			if (rate % 2 == 0)
				g1.fillRect(50, 10, blood_length, 10);
		} else
			g1.fillRect(50, 10, blood_length, 10);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);
		g2.drawString("技能：", 10, 40);
		g2.drawRect(50, 30, 99, 10);
		g2.fillRect(50, 30, skill_length, 10);
		Graphics2D g3 = (Graphics2D) g;
		g3.setColor(Color.GREEN);
		g3.drawString("分数：", 300, 20);
		g3.drawString(String.valueOf(score), 340, 20);

		Graphics2D g4 = (Graphics2D) g;
		g4.setColor(Color.WHITE);
		g4.drawString(String.valueOf(blood_length) + "%", 160, 20);
		g4.drawString(String.valueOf(skill_length / 33 + " 次"), 160, 40);

		if (bossApear == true) {
			g1.drawString("boss:", 10, 60);
			g1.drawRect(50, 50, 100, 10);
			g1.fillRect(50, 50, boss_blood, 10);
		}

	}

	private void outRange() {
		for (Bullet b : bullet_list)
			if (b.x < 0 || b.x > 380 || b.y < 0 || b.y + b.height > 600) {
				die_bullet.add(b);
			}
		bullet_list.removeAll(die_bullet);
		die_bullet.clear();

		for (Enemy e : enemy_list)
			if (e.y > 570 - e.height / 2) {
				die_enemy.add(e);
			}
		enemy_list.removeAll(die_enemy);
		die_enemy.clear();

		for (Skill s : skill_list) {
			if (s.y < -s.height) {
				die_skill.add(s);
				skillApear = false;
			}
		}
		skill_list.removeAll(die_skill);
		die_skill.clear();

		for (Gift g : gift_list) {
			if (g.y > 600)
				die_gitf.add(g);
		}
		gift_list.removeAll(die_gitf);
		die_gitf.clear();
	}

	private void remove() {
		if (rate % 5 == 0) {
			explosion_list.removeAll(die_explosion);
			die_explosion.clear();
			bullet_list.removeAll(die_bullet);
			die_bullet.clear();
			skill_list.removeAll(die_skill);
			die_skill.clear();
			boss_list.removeAll(die_boss);
			die_boss.clear();
		}
	}

	private void Apear() {
		bg.move(rate);

		if (rate % 300 == 0) {// 每过一段时间加血
			hero.bloodAdd();
			blood_length = hero.getBlood();
		}

		if (gift_num == 0) {// 初始化hero子弹
			if (rate % 10 == 0) {
				if (music)
					herobullet.loop();
				Bullet b1 = hero.fire(Resources.herobulletPNG, hero.x + 7,
						hero.y, 0);
				Bullet b3 = hero.fire(Resources.herobullet1PNG, hero.x + 17,
						hero.y - 10, 0);
				Bullet b2 = hero.fire(Resources.herobulletPNG, hero.x + 27,
						hero.y, 0);
				bullet_list.add(b1);
				bullet_list.add(b2);
				bullet_list.add(b3);
			}
		} else {
			if (gift_num == 2) {// 获得2个gift后子弹为7发，1个为5发
				if (rate % 8 == 0) {
					Bullet b1 = hero.fire(Resources.herobulletPNG, hero.x + 17,
							hero.y, -3);
					Bullet b7 = hero.fire(Resources.herobulletPNG, hero.x + 17,
							hero.y, 3);
					bullet_list.add(b1);
					bullet_list.add(b7);
				}
			}
			if (rate % 8 == 0 && gift_num != 0) {
				Bullet b2 = hero.fire(Resources.herobulletPNG, hero.x + 17,
						hero.y, -2);
				Bullet b3 = hero.fire(Resources.herobullet1PNG, hero.x + 17,
						hero.y - 10, -1);
				Bullet b4 = hero.fire(Resources.herobulletPNG, hero.x + 17,
						hero.y - 10, 0);
				Bullet b5 = hero.fire(Resources.herobullet1PNG, hero.x + 17,
						hero.y - 10, 1);
				Bullet b6 = hero.fire(Resources.herobulletPNG, hero.x + 17,
						hero.y, 2);
				bullet_list.add(b2);
				bullet_list.add(b3);
				bullet_list.add(b4);
				bullet_list.add(b5);
				bullet_list.add(b6);
			}
		}
		if (rate % 600 == 0) {// gift1出现
			Gift gift = new Gift(new Random().nextInt(380), 0, 25, 25,
					Resources.gift1PNG);
			gift.setId(0);
			gift_list.add(gift);
		}
		if (rate % 1000 == 0) {// gift2出现
			Gift gift = new Gift(new Random().nextInt(380), 0, 20, 30,
					Resources.gift2PNG);
			gift.setId(1);
			gift_list.add(gift);
		}
		if (!bossApear && boss_list.size() == 0) {
			if (rate % (difficulty - 5) == 0) {
				Enemy e1 = new Enemy(new Random().nextInt(370),
						new Random().nextInt(1), 30, 30, Resources.enemy1PNG);
				e1.setSpeed(4);
				e1.setBlood(2);
				e1.setId(1);
				enemy_list.add(e1);
			}
			if (rate % difficulty == 0) {
				Enemy e2 = new Enemy(new Random().nextInt(360),
						new Random().nextInt(1), 40, 40, Resources.enemy2PNG);
				e2.setSpeed(3);
				e2.setBlood(3);
				e2.setId(2);
				enemy_list.add(e2);
			}
			if (rate % (difficulty + 10) == 0) {
				Enemy e3 = new Enemy(new Random().nextInt(350),
						new Random().nextInt(1), 50, 50, Resources.enemy3PNG);
				e3.setSpeed(2);
				e3.setBlood(5);
				e3.setId(3);
				enemy_list.add(e3);
			}
		}

		// boss出现
		if (rate % 1500 == 0 && bossNum == 0) {
			Boss boss = new Boss(150, -100, 200, 150, Resources.boss1PNG);
			boss.setBlood(200);
			boss.setId(1);
			bossApear = true;
			boss_list.add(boss);
			bossNum = 1;
		}
		if (rate % 3000 == 0 && bossNum == 1&&bossApear==false) {
			Boss boss = new Boss(120, -150, 200, 150, Resources.boss2PNG);
			boss.setBlood(300);
			boss.setId(2);
			bossApear = true;
			boss_list.add(boss);
			bossNum = 2;
		}
		if (rate % 5000 == 0 && bossNum == 2&&bossApear==false) {
			Boss boss = new Boss(74, -200, 250, 200, Resources.boss3PNG);
			boss.setBlood(500);
			boss.setId(3);
			bossApear = true;
			boss_list.add(boss);
			// bossNum=4;
		}
	}

	private void kill(FlyObject f) {
		if (rate % 1 == 0) {
			Explosion e = new Explosion(f.x + f.width / 2, f.y + f.height / 2);
			explosion_list.add(e);
			die_explosion.add(e);
		}
	}

	private void addBullet() {// 为敌机添加子弹
		for (Enemy e : enemy_list) {
			if (e.getId() == 1) {
				if (rate % 60 == 0) {
					Bullet b = e.fire(Resources.bullet1PNG, e.x + e.width / 2
							- 2, e.y + e.height, 5, 10, 7);
					bullet_list.add(b);
				}
			}
			if (e.getId() == 2) {
				if (rate % 70 == 0) {
					Bullet b1 = e.fire(Resources.bullet2PNG, e.x + e.width / 2
							- 2, e.y + e.height, 7, 12, 6);
					Bullet b2 = e.fire(Resources.bullet2PNG, e.x + e.width / 2
							- 2, e.y + e.height + 15, 7, 12, 6);
					bullet_list.add(b1);
					bullet_list.add(b2);
				}
			}
			if (e.getId() == 3) {
				if (rate % 80 == 0) {
					Bullet b1 = e.fire(Resources.bullet3PNG, e.x + e.width / 2
							- 12, e.y + e.height, 8, 14, 6);
					Bullet b2 = e.fire(Resources.bullet3PNG, e.x + e.width / 2
							+ 8, e.y + e.height, 8, 14, 6);
					b1.setV(-1);
					b2.setV(1);
					bullet_list.add(b1);
					bullet_list.add(b2);
				}
			}
		}

		// boss添加子弹
		if (rate % 80 == 0 && bossApear) {
			for (Boss b : boss_list) {
				if (b.getId() == 1) {
					Bullet b1 = b.fire(b.x + b.width / 2 - 25, b.y + b.height,
							50, 30, 10, Resources.boss1BulletPNG);
					Bullet b2 = b.fire(b.x + b.width / 2 - 25, b.y + b.height
							+ 50, 50, 30, 10, Resources.boss1BulletPNG);
					bullet_list.add(b1);
					bullet_list.add(b2);
				}
				if (b.getId() == 2) {
					Bullet b1 = b.fire(b.x + b.width / 2 - 25, b.y + b.height,
							20, 20, 10, Resources.bullet1PNG);
					Bullet b2 = b.fire(b.x + b.width / 2 - 25, b.y + b.height
							+ 50, 10, 10, 7, Resources.boss5BulletPNG);
					Bullet b3 = b.fire(b.x + b.width / 2 + 25, b.y + b.height,
							20, 20, 10, Resources.bullet1PNG);
					Bullet b4 = b.fire(b.x + b.width / 2 + 25, b.y + b.height
							+ 50, 10, 10, 7, Resources.boss5BulletPNG);
					bullet_list.add(b1);
					bullet_list.add(b2);
					bullet_list.add(b3);
					bullet_list.add(b4);
				}
				if (b.getId() == 3) {
					Bullet bu = b.fire(b.x + b.width / 2, b.y + b.height / 2,
							10, 400, 8, Resources.boss4BulletPNG);
					Bullet bu1 = b.fire(b.x + b.width / 2 - 75, b.y + b.height
							/ 2, 10, 400, 8, Resources.boss4BulletPNG);
					Bullet bu2 = b.fire(b.x + b.width / 2 + 75, b.y + b.height
							/ 2, 10, 400, 8, Resources.boss4BulletPNG);
					bullet_list.add(bu2);// 添加boss激光效果
					bullet_list.add(bu1);
					bullet_list.add(bu);
				}
			}
		}
		if (rate % 50 == 0 && bossApear) {
			for (Boss b : boss_list) {
				if (b.getId() == 1) {
					Bullet b1 = b.fire(b.x + b.width / 2 - 55, b.y + b.height
							/ 2, 5, 15, 5, Resources.boss3BulletPNG);
					Bullet b2 = b.fire(b.x + b.width / 2 - 55, b.y + b.height
							/ 2 + 36, 5, 15, 5, Resources.boss3BulletPNG);
					Bullet b3 = b.fire(b.x + b.width / 2 - 58, b.y + b.height
							/ 2 + 72, 10, 10, 5, Resources.bullet1PNG);
					Bullet b4 = b.fire(b.x + b.width / 2 + 55, b.y + b.height
							/ 2, 5, 15, 5, Resources.boss3BulletPNG);
					Bullet b5 = b.fire(b.x + b.width / 2 + 55, b.y + b.height
							/ 2 + 36, 5, 15, 5, Resources.boss3BulletPNG);
					Bullet b6 = b.fire(b.x + b.width / 2 + 52, b.y + b.height
							/ 2 + 72, 10, 10, 5, Resources.bullet1PNG);
					bullet_list.add(b1);
					bullet_list.add(b2);
					bullet_list.add(b3);
					bullet_list.add(b4);
					bullet_list.add(b5);
					bullet_list.add(b6);
				}
				if (b.getId() == 2) {
					Bullet b1 = b.fire(b.x + b.width / 2 - 55, b.y + b.height
							- 30, 10, 10, 8, Resources.boss2BulletPNG);
					Bullet b2 = b.fire(b.x + b.width / 2 - 55, b.y + b.height
							- 30, 10, 10, 8, Resources.boss2BulletPNG);
					Bullet b3 = b.fire(b.x + b.width / 2 - 55, b.y + b.height
							- 30, 10, 10, 8, Resources.boss2BulletPNG);
					Bullet b4 = b.fire(b.x + b.width / 2 + 55, b.y + b.height
							- 30, 10, 10, 8, Resources.boss2BulletPNG);
					Bullet b5 = b.fire(b.x + b.width / 2 + 55, b.y + b.height
							- 30, 10, 10, 8, Resources.boss2BulletPNG);
					Bullet b6 = b.fire(b.x + b.width / 2 + 55, b.y + b.height
							- 30, 10, 10, 8, Resources.boss2BulletPNG);
					b1.setV(-2);// 设置子弹散开
					b2.setV(-1);
					b5.setV(1);
					b6.setV(2);
					bullet_list.add(b1);
					bullet_list.add(b2);
					bullet_list.add(b3);
					bullet_list.add(b4);
					bullet_list.add(b5);
					bullet_list.add(b6);
				}
				if (b.getId() == 3) {
					Bullet b1 = b.fire(b.x + b.width / 2 - 75, b.y + b.height
							- 20, 10, 10, 12, Resources.boss3BulletPNG);
					Bullet b2 = b.fire(b.x + b.width / 2 - 75, b.y + b.height
							- 20, 10, 10, 12, Resources.boss2BulletPNG);
					Bullet b3 = b.fire(b.x + b.width / 2 - 75, b.y + b.height
							- 20, 10, 10, 12, Resources.boss2BulletPNG);
					Bullet b4 = b.fire(b.x + b.width / 2 + 75, b.y + b.height
							- 20, 10, 10, 12, Resources.boss2BulletPNG);
					Bullet b5 = b.fire(b.x + b.width / 2 + 75, b.y + b.height
							- 20, 10, 10, 12, Resources.boss2BulletPNG);
					Bullet b6 = b.fire(b.x + b.width / 2 + 75, b.y + b.height
							- 20, 10, 10, 12, Resources.boss3BulletPNG);
					b1.setV(-2);
					b2.setV(-1);
					b5.setV(1);
					b6.setV(2);
					bullet_list.add(b1);
					bullet_list.add(b2);
					bullet_list.add(b3);
					bullet_list.add(b4);
					bullet_list.add(b5);
					bullet_list.add(b6);
				}
			}
		}
	}

	private void enemyDie(Bullet b, Enemy e, List<Enemy> l1, List<Bullet> l2,
			int s) {
		e.bloodLose();
		l2.add(b);
		if (e.getBlood() < 1) {
			kill(e);
			if (music)
				enemydie.play();
			l1.add(e);
			score += s;
		}
	}

	private void collision() {
		for (Enemy e : enemy_list) {
			if (e.collision(hero)) {
				die_enemy.add(e);
				if (music)
					enemydie.play();
				kill(e);
				hero.bloodLose();
				blood_length = hero.getBlood();
			}
		}

		for (Bullet b : bullet_list) {// 子弹打到敌机
			if (b.getBulletSpeed() < 0) {
				for (Enemy e : enemy_list) {
					if (b.collision(e)) {
						if (e.getId() == 1)
							enemyDie(b, e, die_enemy, die_bullet, 200);
						if (e.getId() == 2)
							enemyDie(b, e, die_enemy, die_bullet, 300);
						if (e.getId() == 3)
							enemyDie(b, e, die_enemy, die_bullet, 500);
					}
				}
			}
			if (b.collision(hero)) {
				hero.bloodLose();
				if (music)
					gethurt.play();
				blood_length = hero.getBlood();
				if (hero.getBlood() == 0) {
					kill(hero);
					heroDie();
				}
				kill(b);
				die_bullet.add(b);
				// hero中枪后减少gift
				die_gitf.clear();
				gift_num--;
				if (gift_num <= 0) {
					gift_num = 0;
				}
			}
		}

		for (Skill s : skill_list) {
			for (Enemy e : enemy_list) {
				if (s.collision(e)) {
					die_enemy.add(e);
					if (music)
						enemydie.play();
					kill(e);
					if (e.getId() == 1)
						score += 100;
					if (e.getId() == 2)
						score += 200;
					if (e.getId() == 3)
						score += 300;
				}
			}
			for (Bullet bu : bullet_list)
				if (bu.collision(s))
					die_bullet.add(bu);
		}
		for (Gift g : gift_list) {
			if (g.collision(hero)) {
				if (music)
					getgift.play();
				if (g.getId() == 0) {// 增加子弹的gift
					if (music)
						gift_num++;
					if (gift_num > 2)
						gift_num = 2;
					die_gitf.add(g);
				}
				if (g.getId() == 1) {// 增加技能的gift
					skill_length += 33;
					if (skill_length > 99)
						skill_length = 99;
					die_gitf.add(g);
				}
			}
		}

		// boss
		for (Boss bo : boss_list) {// 用技能打boss分数加成不多
			for (Skill s : skill_list) {
				if (s.collision(bo)) {
					if (bo.getId() == 1) {
						bo.bloodLose(10);
						boss_blood = (int) (bo.getBlood() / 2);
						if (bo.getBlood() == 0) {
							die_boss.add(bo);
							if (music)
								boss1die.play();
							kill(bo);
							score += 10000;// boss死亡奖励10000分
							bossApear = false;
						}
						die_skill.add(s);
						score += 3000;
					}
					if (bo.getId() == 2) {
						bo.bloodLose(10);
						boss_blood = (int) (bo.getBlood() / 3);
						if (bo.getBlood() == 0) {
							die_boss.add(bo);
							if (music)
								boss2die.play();
							kill(bo);
							score += 50000;
							bossApear = false;
						}
						die_skill.add(s);
						score += 3000;
					}
					if (bo.getId() == 3) {
						bo.bloodLose(10);
						die_skill.add(s);
						boss_blood = (int) (bo.getBlood() / 5);
						if (bo.getBlood() == 0) {
							score += 3000;
							die_boss.add(bo);
							gameSuccess();// boss3死亡，游戏结束，胜利
						}
					}
				}
			}

			for (Bullet bl : bullet_list) {// 子弹打boss
				if (bl.getBulletSpeed() < 0)
					if (bl.collision(bo)) {
						bo.bloodLose(1);
						kill(bl);
						die_bullet.add(bl);
						if (bo.getId() == 1) {
							score += 100;
							boss_blood = (int) (bo.getBlood() / 2);
						}
						if (bo.getId() == 2) {
							score += 200;
							boss_blood = (int) (bo.getBlood() / 3);
						}
						if (bo.getId() == 3) {
							score += 300;
							boss_blood = (int) (bo.getBlood() / 5);
						}
						if (bo.getBlood() == 0) {
							die_boss.add(bo);
							kill(bo);
							bossApear = false;
							if (bo.getId() == 1) {
								score += 20000;// 子弹打死boss分数加成比较多
								if (music)
									boss1die.play();
							}
							if (bo.getId() == 2) {
								score += 70000;
								if (music)
									boss2die.play();
							}
							if (bo.getId() == 3) {// boss3死亡，游戏结束，胜利
								score += 120000;
								gameSuccess();
							}
						}
					}
			}

			if (bo.collision(hero)) {// 英雄碰到boss掉血死亡
				hero.bloodLose();
				blood_length = hero.getBlood();
				heroDie();
			}
		}

	}

	private void gameSuccess() {
		playing = false;
		bossApear = false;
		if (music) {
			herobullet.stop();
			// boss3die.play();
			success.loop();
		}
		startBtn.setVisible(true);
		difficultyBtn.setVisible(true);
		musicBtn.setVisible(true);
		exitBtn.setVisible(true);
	}

	private void heroDie() {
		playing = false;
		if (music) {
			herodie.play();
			herobullet.stop();
		}
		kill(hero);
		startBtn.setVisible(true);
		difficultyBtn.setVisible(true);
		musicBtn.setVisible(true);
		exitBtn.setVisible(true);
		blood_length = 0;
		difficultyBtn.setText("难度(简单)");
	}

	private void objectMove() {
		Apear();
		for (Enemy e : enemy_list)
			e.move(rate);
		for (Bullet b : bullet_list)
			b.move();
		for (Skill s : skill_list)
			s.move();
		for (Boss b : boss_list) {
			b.moveDown();
			b.move();
		}
		for (Skill s : skill_list)
			s.move();
		for (Gift gf : gift_list)
			gf.move();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			key_up = true;
			break;
		case KeyEvent.VK_DOWN:
			key_down = true;
			break;
		case KeyEvent.VK_RIGHT:
			key_right = true;
			break;
		case KeyEvent.VK_LEFT:
			key_left = true;
			break;
		case KeyEvent.VK_SPACE:
			if (!skillApear) {// 保证一次只能放一个大招
				key_space = true;
				skill_length -= 33;
				if (music && skill_length > 0)
					skill.play();
			}
			if (skill_length <= 0) {
				skill_length = 0;
				key_space = false;
			}
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			key_up = false;
			break;
		case KeyEvent.VK_DOWN:
			key_down = false;
			break;
		case KeyEvent.VK_RIGHT:
			key_right = false;
			break;
		case KeyEvent.VK_LEFT:
			key_left = false;
			break;
		case KeyEvent.VK_SPACE:
			key_space = false;
			break;
		}
	}

	private void keyAction() {
		if (key_space == true) {// 大招
			for (int i = 10; i < 360; i += 40) {
				Skill s = new Skill(i/* new Random().nextInt(350) */, 600, 50,
						90, Resources.bomb_fPNG);
				if (skill_list.size() != 9) {
					skill_list.add(s);
					skillApear = true;
				}
			}
		}
		int d = 8;
		if (key_up == true && key_down == false && key_right == false
				&& key_left == false)
			hero.moveUp(d);
		else if (key_up == false && key_down == true && key_right == false
				&& key_left == false)
			hero.moveDown(d);
		else if (key_up == false && key_down == false && key_right == true
				&& key_left == false)
			hero.moveRight(d);
		else if (key_up == false && key_down == false && key_right == false
				&& key_left == true)
			hero.moveLeft(d);
		else if (key_up == true && key_down == false && key_right == true
				&& key_left == false) {
			hero.moveRight(d);
			hero.moveUp(d);
		} else if (key_up == true && key_down == false && key_right == false
				&& key_left == true) {
			hero.moveLeft(d);
			hero.moveUp(d);
		} else if (key_up == false && key_down == true && key_right == true
				&& key_left == false) {
			hero.moveDown(d);
			hero.moveRight(d);
		} else if (key_up == false && key_down == true && key_right == false
				&& key_left == true) {
			hero.moveDown(d);
			hero.moveLeft(d);
		}
	}
}
