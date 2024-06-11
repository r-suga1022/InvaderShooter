import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class Enemy extends BaseObject{
	private int	m_HP;
	private int m_Def;			// –hŒä—Í
	private int m_AppearTime;	// oŒ»ŠÔ
	private int m_bulletType;	// ’eƒ^ƒCƒv
	private int m_bulletIntvl;	// ”­ËŠÔŠu
	private int m_bulletSpeed;	// ’e‘¬“x
	private int m_enemytype;   //“Gƒ^ƒCƒv
	// ’e‚ÌŒ©‚½–Úƒ^ƒCƒvi“¯‚¶’e–‹ƒ^ƒCƒv‚Å‚àŒ©‚½–Ú‚ª
	//ˆá‚¤•¨‚ğì‚ê‚é‚æ‚¤‚Éj
	private int m_appearancetype;

	public final static int BL_1WAY_MON	=	0;
	public final static int BL_8WAY_ALL	= 	1;
	public final static int BL_3WAY_FAN	= 	2;
	public final static int KB_2WAY_DI	=	3;
	
	private EnemyManager _manager = null;

	private Fighter _fighter = null;
	public Fighter GetFighter(){return _fighter;}
	
	public Enemy(EnemyManager em)
	{
		super();
		m_HP = 0;
		m_Def = 0;
		m_AppearTime = 0;
		m_bulletType = 0;
		m_bulletIntvl = 0;
		m_bulletSpeed = 0;
		m_enemytype = 0;
		_fighter = em.GetFighter();
	
		_manager = em;
	}

//----------ƒQƒbƒ^[‚ÆƒZƒbƒ^[-----------
	public int GetAppearTime()
	{
		return m_AppearTime;
	}


	public int GetBulletType()
	{
		return m_bulletType;
	}

	public int GetEnemyType()
	{
		return m_enemytype;
	}

	public void SetEnemyType(int typeE)
	{
		m_enemytype = typeE;
	}

	public void SetHP(int hp)
	{
		m_HP = hp;
	}

	public void SetDEF(int def)
	{
		m_Def = def;
	}

	public void SetBulletType(int type)
	{
		m_bulletType = type;
	}

	public void SetAppearTime(int apptime)
	{
		m_AppearTime = apptime;
	}

	public void SetBulletIntvl(int interval)
	{
		m_bulletIntvl = interval;
	}

	public void SetBulletSpeed(int Speed)
	{
		m_bulletSpeed = Speed;
	}

	public void SetAppearanceType(int at)
	{
		m_appearancetype = at;
	}


	public int GetAppearanceType()
	{
		return m_appearancetype;
	}
//----------‚±‚±‚Ü‚Å-----------

	public void DecreaseHP(int n)
	{
		m_HP -= (n - m_Def);
		//HPŒ¸­‚ÌŒvZ®
		if(m_HP < 0){
			if(this._fighter != null){
				int num = _fighter.GetKilln();
				_fighter.SetKilln(num + 1);		
			}
			Enable(false);
		}
	}

	//’e¶¬
	public void Fire()
	{
		if(!isEnable) return;

		if(_manager.GetTime() % m_bulletIntvl == 0){
			switch(m_bulletType){
				case 0:
				CreatePosBullet('x');
				break;

				case 1:
				CreateEimsBullet();
				break;

				case 2:
				CreateRanBullet();
				CreateRanBullet();
				CreateRanBullet();
				break;

				case 3:
				CreatePosBullet('y');

				default:
				CreateEimsBullet();
			}
		}
	}

	//‚±‚±‚Å“G‚ÌŒ©‚½–Ú‚ğ•ÏX
	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;

		switch(m_enemytype){
			case 0:
			Image img = Toolkit.getDefaultToolkit().getImage("enemy0.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
			break;

			case 1:
			img = Toolkit.getDefaultToolkit().getImage("enemy1.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
			break;

			case 2:
			img = Toolkit.getDefaultToolkit().getImage("enemy2.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);
			break;

			default:
			img = Toolkit.getDefaultToolkit().getImage("enemy1.png");
        	g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY), null);

		}
	}

	// ‘_‚¢’e‚ğ¶¬
	public void CreateEimsBullet()
	{
		Fighter fighter = _manager.GetFighter();
		Bullet[] bullet = _manager.GetBullet();
		
		if(!isEnable) return;

		if(fY > 240) return;

		for(int i=0; i<EnemyManager.BULLET_MAX; i++)
		{
			if(bullet[i].isEnable) continue;

			float dx, dy, d, speed;

			dx = fighter.GetX() - fX;
			dy = fighter.GetY() + 23 - fY;

			speed = this.m_bulletSpeed;

			d = (float)Math.sqrt(dx*dx + dy*dy);

			bullet[i].SetPos(fX, fY);
			bullet[i].SetVX((dx/d)*speed);
			bullet[i].SetVY((dy/d)*speed);

			bullet[i].Enable(true);

			bullet[i].setBullettype(m_bulletType);
			bullet[i].setAppearancetype(m_appearancetype);

			break;
		}
	}
	//’e–‹í—Ş’Ç‰ÁFc‰¡ŒÅ’è’e
	public void CreatePosBullet(char vec)
	{
		Fighter fighter = _manager.GetFighter();
		Bullet[] bullet = _manager.GetBullet();
		
		if(!fighter.IsEnable()) return;
		if(!isEnable) return;

		if(fY > 240) return;

		for(int i=0; i<EnemyManager.BULLET_MAX; i++)
		{
			if(bullet[i].isEnable) continue;

			bullet[i].SetPos(fX, fY);
			int speed;
			if(vec == 'x' || vec == 'y'){
				speed = this.m_bulletSpeed; 
			}else{
				speed = (int)fVY;
			}

			if(vec == 'x'){
				bullet[i].SetVX(speed);
				bullet[i].SetVY(3);
			}else{
				bullet[i].SetVX(0);
				bullet[i].SetVY(speed);
			}
			

			bullet[i].Enable(true);

			bullet[i].setBullettype(m_bulletType);
			bullet[i].setAppearancetype(m_appearancetype);

			break;
		}
	}

	//’e–‹í—Ş’Ç‰ÁF—”’e
	public void CreateRanBullet()
	{
		Fighter fighter = _manager.GetFighter();
		Bullet[] bullet = _manager.GetBullet();
		
		if(!fighter.IsEnable()) return;
		if(!isEnable) return;
		if(fY > 240) return;

		for(int i=0; i<EnemyManager.BULLET_MAX; i++)
		{
			if(bullet[i].isEnable) continue;

			bullet[i].SetPos(fX, fY);

			Random rand = new Random();
			int vx = rand.nextInt(40) - 20;
			int vy = rand.nextInt(10) + 10;

			bullet[i].SetVX(vx);
			bullet[i].SetVY(vy);			

			bullet[i].Enable(true);

			bullet[i].setBullettype(m_bulletType);
			bullet[i].setAppearancetype(m_appearancetype);

			break;
		}
	}

	//’e–‹í—Ş’Ç‰ÁF•ûŒüw’è’e
	public void CreatewayBullet(int vx)
	{
		Fighter fighter = _manager.GetFighter();
		Bullet[] bullet = _manager.GetBullet();
		
		if(!fighter.IsEnable()) return;
		if(!isEnable) return;
		if(fY > 240) return;

		for(int i=0; i<EnemyManager.BULLET_MAX; i++)
		{
			if(bullet[i].isEnable) continue;

			bullet[i].SetPos(fX, fY);

			int speed = this.m_bulletSpeed;

			bullet[i].SetVX(vx);
			bullet[i].SetVY(speed);			

			bullet[i].Enable(true);

			bullet[i].setBullettype(m_bulletType);
			bullet[i].setAppearancetype(m_appearancetype);

			break;
		}
	}
}
