import java.util.Random;
import java.awt.Graphics2D;

// �A�C�e���Ɋւ��鏈�����s��
class ItemManager {
    public final static int ITEM_MAX = 10;
    public final static int ITEM_CREATE_RATE = 100; // �A�C�e����ITEM_CREATE_RATE���1��̊����ō��
    
    private Fighter _fighter; // ���@�̃I�u�W�F�N�g
    public Fighter GetFighter() { return _fighter; }

    private Item _item[] = new Item[ITEM_MAX]; // �X�e�[�W���̃A�C�e���̃f�[�^������
    public Item[] GetItem() { return _item; }

    private int numItem; // �A�C�e���̐���\��
    public int GetItemNumber() { return numItem; }

    private int timer; // �Q�[��������
    public int GetTime() { return timer; }

    private SoundEffect _effect; // �T�E���h�G�t�F�N�g
    public void setSoundEffect(SoundEffect effect) { _effect = effect; }

    // �R���X�g���N�^
    public ItemManager(MainGameState main) {
        _fighter = main.GetFighter();
        numItem = 0;
    }

    // �A�C�e����`��
    public void Show(Graphics2D g2) {
        for(int i = 0; i < numItem; ++i) {
            if(_item[i].isEnable == true) {
                _item[i].Show(g2);
            }
        }
    }

    // �A�C�e����V���ɓo�^����
    public void ItemCreate() {
        Random rand = new Random();
        Random typebase = new Random();
        // 0����ITEM_CREATE_RATE�܂ł̐����Ƃ��ė����𔭐�������
        int WhetherCreate = rand.nextInt(ITEM_CREATE_RATE)+1;
        int type;

        // �A�C�e����o�^����
        if(WhetherCreate == ITEM_CREATE_RATE) {
        //if(timer % WhetherCreate == 0) {
            if(numItem < ITEM_MAX) {
                System.out.println("Yes");// �f�o�b�O�p
                Item temp = new Item();
                type = typebase.nextInt(temp.getTypeVariation());
                int new_fx = rand.nextInt(480); // �o������x���W
                int new_fvy = rand.nextInt(5) + 5; // �ǂ̂悤�ɓ�����(5����10�̊�)
                temp.SetPos(new_fx, 0); // �o�����W��o�^
                temp.SetVY(new_fvy); // ���x��o�^
                temp.setType(type);
                temp.Enable(true);
                _item[numItem++] = temp;
            }
        }
    }

    // �A�C�e���𓮂���
    public void ItemMove() {
        for(int i=0; i < numItem; ++i) {
            // isEnable��false�������瓮�����Ȃ�
            if(!_item[i].IsEnable()) continue;

            _item[i].Move();

            // �͈͂̊O�ɏo����isEnable��false�ɂ���B
            if(((_item[i].GetX() >= 530)||(_item[i].GetX() <= -50))||((_item[i].GetY() >= 690)||(_item[i].GetY() <= -50)))
            _item[i].Enable(false);
        }
    }

    public void update(int time) {
        timer = time;
        ItemCreate();

        ItemMove();

        // �����蔻��̃��\�b�h�͂����Ə�ʂ̃N���X�ōs�������ǂ���������Ȃ�
        HitCheckItemAndFighter();
    }

    // ���@�ƃA�C�e���̓����蔻��
    public boolean HitCheckItemAndFighter() {
        if(!_fighter.IsEnable()) return false;
        for(int i = 0; i < numItem; ++i) {
            if(!_item[i].IsEnable()) continue;

            float dx, dy, width, height;

            dx = _fighter.GetX() - _item[i].GetX();
            dy = _fighter.GetY() - _item[i].GetY();

            width = 50; height = 60;

            // ��������
            if((Math.abs(dx) <= width) && (Math.abs(dy) <= height)) {
                System.out.println("item kind");//

                int n = _fighter.GetnLeft(), a = _fighter.GetAttack();
                // �p���[�A�b�v
                if(_item[i].getType() == _item[i].getPowerUpType()) {
                    if(a < _fighter.GetAttackMax()) {
                        if(_fighter.GetAttackMax() - a < _fighter.GetAttackDecrease()) {
                            _fighter.SetAttack(_fighter.GetAttackMax());
                        } else {
                            _fighter.SetAttack(a + _fighter.GetAttackDecrease());
                        }
                    } else {}

                // �c�@�A�b�v
                } else if(_item[i].getType() == _item[i].getLeftUpType()) {
                    if(n < _fighter.MAX_NLEFT) {_fighter.SetnLeft(n+1);}

                // �p���[�A�c�@�_�E��
                } else if(_item[i].getType() == _item[i].getPowerDownType())  {
                    if(a > _fighter.GetAttackMin()) {
                        if(a - _fighter.GetAttackMin() < _fighter.GetAttackDecrease()) {
                            _fighter.SetAttack(_fighter.GetAttackMin());
                        } else {
                            _fighter.SetAttack(a - _fighter.GetAttackDecrease());
                        }
                    } else {}
                    if(_fighter.GetnLeft() > 1){
                        _fighter.SetnLeft(n-1);
                        _fighter.Enable(false);
                        _fighter.SetMuteki(1);
                        _fighter.SetMbegin(timer);
                    }
                    
                }

                _item[i].Enable(false);
                if(_item[i].getType() == _item[i].getPowerDownType()) {
                    _effect.PlayPowerDown();
                    return true;
                } else {
                    _effect.PlayItemGet();
                }
            }
        }
        return false;
    }

}