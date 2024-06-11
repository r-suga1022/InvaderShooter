import java.awt.Graphics2D;

// 全ての基底クラス
public class BaseObject {
	protected float fX;
	protected float fY;
	protected float fVX;
	protected float fVY;
	protected boolean isEnable;

	//
	public BaseObject()
	{
		fX = 0;
		fY = 0;
		fVX = fVY = 0;
		isEnable = false;
	}

	//
	public float GetX()
	{
		return fX;
	}

	public float GetY()
	{
		return fY;
	}

	//
	public boolean IsEnable()
	{
		return isEnable;
	}

	// 座標の設定

	//
	public void SetPos(float x, float y)
	{
		fX = x;
		fY = y;
	}

	//
	public void SetVX(float x)
	{
		fVX = x;
	}

	//
	public void SetVY(float y)
	{
		fVY = y;
	}

	
	public float GetVX()
	{
		return fVX;
	}

	
	public float GetVY()
	{
		return fVY;
	}

	//
	public void Enable(boolean flag)
	{
		isEnable = flag;
	}

	//動かす
	public void Move()
	{
		if(!this.isEnable) return;

		fX += fVX;
		fY += fVY;
	}

	// 描画する
	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;
	}


}
