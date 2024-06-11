import java.awt.*;
import java.awt.geom.*;

public class BossBullet extends Bullet{
    // �e�^�C�v
    private int bossbullettype;
    public void setBossBulletType(int type) { bossbullettype = type; }
    public int getBossBulletType() { return bossbullettype;}
    // ���˂��ꂽ����
    private int shootedtime;
    public void setShootedTime(int st) { shootedtime = st;}
    public int getShootedTime() {return shootedtime;}

    // -----------------�ԉΊ֘A�̃t�B�[���h---------------//
    private boolean isExploded = false;
    public void setIsExploded(boolean flag) { isExploded = flag;}
    public boolean getIsExploded() { return isExploded;}
    
    // �R���X�g���N�^
    public BossBullet() {
        super();
    }

    public void Show(Graphics2D g2) {
        super.Show(g2);        
    }

    // �e���L���łȂ��Ȃ�����ꏏ�ɉԉΔ����������̃t���O��false�ɂ���
    @Override
    public void Enable(boolean flag) {
        super.Enable(flag);
        if(!IsEnable()) {
            setIsExploded(flag);
            setShootedTime(0);
        }
    } 
}
