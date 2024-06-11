import java.awt.*;
import java.awt.geom.*;

public class BossBullet extends Bullet{
    // 弾タイプ
    private int bossbullettype;
    public void setBossBulletType(int type) { bossbullettype = type; }
    public int getBossBulletType() { return bossbullettype;}
    // 発射された時間
    private int shootedtime;
    public void setShootedTime(int st) { shootedtime = st;}
    public int getShootedTime() {return shootedtime;}

    // -----------------花火関連のフィールド---------------//
    private boolean isExploded = false;
    public void setIsExploded(boolean flag) { isExploded = flag;}
    public boolean getIsExploded() { return isExploded;}
    
    // コンストラクタ
    public BossBullet() {
        super();
    }

    public void Show(Graphics2D g2) {
        super.Show(g2);        
    }

    // 弾が有効でなくなったら一緒に花火爆発したかのフラグをfalseにする
    @Override
    public void Enable(boolean flag) {
        super.Enable(flag);
        if(!IsEnable()) {
            setIsExploded(flag);
            setShootedTime(0);
        }
    } 
}
