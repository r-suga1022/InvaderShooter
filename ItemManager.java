import java.util.Random;
import java.awt.Graphics2D;

// アイテムに関する処理を行う
class ItemManager {
    public final static int ITEM_MAX = 10;
    public final static int ITEM_CREATE_RATE = 100; // アイテムをITEM_CREATE_RATE回に1回の割合で作る
    
    private Fighter _fighter; // 自機のオブジェクト
    public Fighter GetFighter() { return _fighter; }

    private Item _item[] = new Item[ITEM_MAX]; // ステージ内のアイテムのデータを入れる
    public Item[] GetItem() { return _item; }

    private int numItem; // アイテムの数を表す
    public int GetItemNumber() { return numItem; }

    private int timer; // ゲーム内時間
    public int GetTime() { return timer; }

    private SoundEffect _effect; // サウンドエフェクト
    public void setSoundEffect(SoundEffect effect) { _effect = effect; }

    // コンストラクタ
    public ItemManager(MainGameState main) {
        _fighter = main.GetFighter();
        numItem = 0;
    }

    // アイテムを描画
    public void Show(Graphics2D g2) {
        for(int i = 0; i < numItem; ++i) {
            if(_item[i].isEnable == true) {
                _item[i].Show(g2);
            }
        }
    }

    // アイテムを新たに登録する
    public void ItemCreate() {
        Random rand = new Random();
        Random typebase = new Random();
        // 0からITEM_CREATE_RATEまでの整数として乱数を発生させる
        int WhetherCreate = rand.nextInt(ITEM_CREATE_RATE)+1;
        int type;

        // アイテムを登録する
        if(WhetherCreate == ITEM_CREATE_RATE) {
        //if(timer % WhetherCreate == 0) {
            if(numItem < ITEM_MAX) {
                System.out.println("Yes");// デバッグ用
                Item temp = new Item();
                type = typebase.nextInt(temp.getTypeVariation());
                int new_fx = rand.nextInt(480); // 出現するx座標
                int new_fvy = rand.nextInt(5) + 5; // どのように動くか(5から10の間)
                temp.SetPos(new_fx, 0); // 出現座標を登録
                temp.SetVY(new_fvy); // 速度を登録
                temp.setType(type);
                temp.Enable(true);
                _item[numItem++] = temp;
            }
        }
    }

    // アイテムを動かす
    public void ItemMove() {
        for(int i=0; i < numItem; ++i) {
            // isEnableがfalseだったら動かさない
            if(!_item[i].IsEnable()) continue;

            _item[i].Move();

            // 範囲の外に出たらisEnableをfalseにする。
            if(((_item[i].GetX() >= 530)||(_item[i].GetX() <= -50))||((_item[i].GetY() >= 690)||(_item[i].GetY() <= -50)))
            _item[i].Enable(false);
        }
    }

    public void update(int time) {
        timer = time;
        ItemCreate();

        ItemMove();

        // 当たり判定のメソッドはもっと上位のクラスで行う方が良いかもしれない
        HitCheckItemAndFighter();
    }

    // 自機とアイテムの当たり判定
    public boolean HitCheckItemAndFighter() {
        if(!_fighter.IsEnable()) return false;
        for(int i = 0; i < numItem; ++i) {
            if(!_item[i].IsEnable()) continue;

            float dx, dy, width, height;

            dx = _fighter.GetX() - _item[i].GetX();
            dy = _fighter.GetY() - _item[i].GetY();

            width = 50; height = 60;

            // 当たった
            if((Math.abs(dx) <= width) && (Math.abs(dy) <= height)) {
                System.out.println("item kind");//

                int n = _fighter.GetnLeft(), a = _fighter.GetAttack();
                // パワーアップ
                if(_item[i].getType() == _item[i].getPowerUpType()) {
                    if(a < _fighter.GetAttackMax()) {
                        if(_fighter.GetAttackMax() - a < _fighter.GetAttackDecrease()) {
                            _fighter.SetAttack(_fighter.GetAttackMax());
                        } else {
                            _fighter.SetAttack(a + _fighter.GetAttackDecrease());
                        }
                    } else {}

                // 残機アップ
                } else if(_item[i].getType() == _item[i].getLeftUpType()) {
                    if(n < _fighter.MAX_NLEFT) {_fighter.SetnLeft(n+1);}

                // パワー、残機ダウン
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