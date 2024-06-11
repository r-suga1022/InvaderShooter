// 一部参考にしたサイト
// https://nompor.com/2017/12/06/post-1665/

import java.awt.*;
import java.awt.geom.*;
import java.nio.file.ProviderMismatchException;
import java.util.Random;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;

// ボスのベースクラス
public class Boss extends BaseObject {
    
    // オブジェクト関連のフィールド
    protected BossManager _bmanager;
    private int BOSSBULLET_MAX; // 画面上に表示できる弾幕の最大数
    public int GetBossBulletMax() {return BOSSBULLET_MAX;}
    public void SetBossBulletMax(int bm) { BOSSBULLET_MAX = bm; }
    private BossBullet[] _bullet; // 画面上の弾幕
    public BossBullet[] GetBossBullet() { return _bullet; }
    public void SetBossBullet(BossBullet[] bb) {_bullet = bb;}
    private Fighter _fighter;
    public Fighter GetFighter() {return _fighter;}
    public void SetFighter(Fighter f) {_fighter = f;}
    private SoundEffect _effect; // サウンドエフェクトのオブジェクト
    public void setSoundEffect(SoundEffect ef) {_effect = ef;}
    public SoundEffect getSoundEffect() {return _effect;}

    // 時間に関するフィールド
    private int _time; // ゲーム内時間
    public void setTime(int time) { _time = time; }
    public int getTime() { return _time;}
    private int StartTime; // ボスの出現ステートが始まる時間
    public int getStartTime() { return StartTime;}
    public void setStartTime(int time) { StartTime = time; }
    private int AppearSpan; // 出現ステートの継続時間
    public void setAppearSpan(int as) { AppearSpan = as;}
    public int getAppearSpan() { return AppearSpan;}
    private int AppearFinishTime; // 出現ステートが終わる時間
    public void setAppearFinishTime(int aft) { AppearFinishTime = aft;}
    public int getAppearFinishTime() {return AppearFinishTime;}
    private int AppearBattleIntvl; // 出現ステートが終わってから戦闘ステートが開始するまでの空き時間
    public void setAppearBattleIntvl(int abi) {AppearBattleIntvl = abi;}
    public int getAppearBattleIntvl() {return AppearBattleIntvl;}
    private int BattleStartTime; // 戦闘ステート（状態1）が開始する時間
    public void setBattleStartTime(int bst) { BattleStartTime = bst;}
    public int getBattleStartTime() {return BattleStartTime;}
    private int State1FinishTime; // 状態1が終了した時間
    public void setState1FinishTime(int time) { State1FinishTime = time;}
    public int getState1FinishTime() { return State1FinishTime;}
    private int State1and2intvl; // 状態1と状態2の間のインターバル
    public void setState1and2Intvl(int si) {State1and2intvl = si;}
    public int getState1and2Intvl() { return State1and2intvl;}
    private int BattleState2Start; // 状態2の開始時間
    public void setBattleState2Start(int bss) { BattleState2Start = bss;}
    public int getBattleState2Start() { return BattleState2Start;}
    private int State2FinishTime; // 状態2が終了した時間
    public void setState2FinishTime(int time) {State2FinishTime = time;}
    public int getState2FinishTime() { return State2FinishTime;}
    private int State2and3intvl; // 状態2と状態3の間のインターバル
    public void setState2and3Intvl(int si) { State2and3intvl = si;}
    public int getState2and3Intvl() {return State2and3intvl;}
    private int BattleState3Start; // 状態3の開始時間
    public void setBattleState3Start(int bss) { BattleState3Start = bss;}
    public int getBattleState3Start(){return BattleState3Start;}
    private int State3FinishTime; // 状態3の終了時間
    public void setState3FinishTime(int sft) {State3FinishTime = sft;}
    public int getState3FinishTime() { return State3FinishTime;}
    private int DeathStateStart; // 死亡ステート開始時間
    public void setDeathStateStart(int dss) { DeathStateStart = dss;}
    public int getDeathStateStart() { return DeathStateStart;}
    private int BossWinkIntvl; // ボスの点滅間隔
    public void setBossWinkIntvl(int bwi) {BossWinkIntvl = bwi;}
    public int getBossWinkIntvl() {return BossWinkIntvl;}
    private int ScreenWinkIntvl; // 画面が白く点滅する間隔
    public void setScreenWinkIntvl(int swi) {ScreenWinkIntvl = swi;}
    public int getScreenWinkIntvl() { return ScreenWinkIntvl;}
    private int BossWinkSpan; // ボスが点滅している時間
    public void setBossWinkSpan(int bws) { BossWinkSpan = bws;}
    public int getBossWinkSpan() {return BossWinkSpan;}
    private int ScreenWinkSpan; // 画面が白く点滅する時間
    public void setScreenWinkSpan(int sws) {ScreenWinkSpan = sws;}
    public int getScreenWinkSpan() {return ScreenWinkSpan;}
    private int WinkFinishSpan; // 爆発からクリア音楽を流し始めるまでの間の時間
    public void setWinkFinishSpan(int wfs) {WinkFinishSpan = wfs;}
    public int getWinkFinishSpan() {return WinkFinishSpan;}
    private int ClearBGMSpan; // クリア音楽を流し始めてからクリア画面に移動するまでの時間
    public void setClearBGMSpan(int cbs) { ClearBGMSpan = cbs;}
    public int getClearBGMSpan() { return ClearBGMSpan;}

    // 自機の位置や速度に関するフィールド
    private float fVX_appear = 0f; // 出現ステート開始座標x
    private float fVY_appear = 1f; // 出現ステート開始座標y
    public void setVAppear(float x, float y) { fVX_appear = x; fVY_appear = y;}
    public float getVXAppear() { return fVX_appear;}
    public float getVYAppear() { return fVY_appear;}
    private float State1StartX; // 第一段階の開始座標x
    private float State1StartY; // 第一段階の開始座標y
    public void setState1StartPos(float x, float y) { State1StartX = x; State1StartY = y;}
    public float getState1StartX() { return State1StartX; }
    public float getState1StartY() { return State1StartY;}
    private float State2StartX; // 第二段階の開始座標x
    private float State2StartY; // 第二段階の開始座標y
    public void setState2StartPos(float x, float y) { State2StartX = x; State2StartY = y;}
    public float getState2StartX() { return State2StartX;}
    public float getState2StartY() { return State2StartY;}
    private float State3StartX; // 第三段階の開始座標x
    private float State3StartY; // 第三段階の開始座標y
    public void setState3StartPos(float x, float y) {State2StartX = x; State2StartY = y;}
    public float getState3StartX() {return State3StartX;}
    public float getState3StartY() {return State3StartY;}

    // 文字列の位置や時間に関するフィールド
    private int AppearStringIntvl; // "Warning!"の点滅周期(例：10の時は5秒で表示、5秒で非表示のような点滅)
    public void setAppearStringIntvl(int asi) { AppearStringIntvl = asi;}
    public int getAppearStringIntvl() { return AppearStringIntvl; }
    private int AppearStringX;
    private int AppearStringY;
    public void setAppearStringPos(int x, int y) {AppearStringX = x; AppearStringY = y;}
    public int getAppearStringX() { return AppearStringX;}
    public int getAppearStringY() { return AppearStringY;}

    // HPに関するフィールド
    private int HP_MAX; // 満タンのHP値
    public void setHP_MAX(int maxHP) {HP_MAX = maxHP;}
    public int getHP_MAX() {return HP_MAX;}
    private int HP; // 現在のHP
    public void setHP(int newHP) { HP = newHP; }
    public int getHP() {return HP;}

    // ボスの弾幕関連
    private int eim_speed; // 自機狙い弾の速度
    public void setEimSpeed(int es) { eim_speed = es;}
    public int getEimSpeed() { return eim_speed;}
    private int eim_intvl; // 自機狙い弾の発射間隔
    public void SetEimIntvl(int i) {eim_intvl = i;}
    public int GetEimIntvl() { return eim_intvl; }
    private int rotate_intvl; // 渦巻き型弾の発車間隔
    public void SetRotateIntvl(int si) {rotate_intvl = si;}
    public int GetRotateIntvl() { return rotate_intvl; }
    private int rotate_speed; // 渦巻き型弾の速度
    public void SetRotateSpeed(int ss) { rotate_speed = ss;}
    public int GetRotateSpeed() { return rotate_speed;}
    private int current_angle; // 現在の発射角度
    public void SetCurrentAngle(int ca) {current_angle = ca;}
    public int GetCurrentAngle() {return current_angle;}
    public int angle_adjustX; // 発射角度調整（x方向）
    public int angle_adjustY; // 発射角度調整（y方向）
    public void SetAngleAdjust(int x, int y) { angle_adjustX = x; angle_adjustY = y;}
    public int GetAngleAdjustX() { return angle_adjustX;}
    public int GetAngleAdjustY() { return angle_adjustY;}
    private int firework_speed; // 花火の発射速度
    public void SetFireworkSpeed(int fs) { firework_speed = fs;}
    public int GetFireworkSpeed() { return firework_speed;}
    private int firework_shoot_intvl; // 花火の発射間隔
    public void SetFireworkShootIntvl(int fsi) { firework_shoot_intvl = fsi;}
    public int GetFireworkShootIntvl() {return firework_shoot_intvl;}
    private int piece_num; // 花火の爆発後の欠片の数
    public void SetPieceNum(int pn) { piece_num = pn;}
    public int GetPieceNum() { return piece_num;}
    private int shoot_explode_intvl; // 発射後爆発するまでの時間
    public void SetShootExplodeIntvl(int sei) { shoot_explode_intvl = sei;}
    public int GetShootExplodeIntvl() { return shoot_explode_intvl;}
    private float velocitytimesfirework; // 爆発前と後で速さが何倍になるか
    public void SetVelocityTimesFirework(float vt) { velocitytimesfirework = vt;}
    public float GetVelocityTimesFirework() { return velocitytimesfirework;}
    private int radiation_speed; // 放射弾の発射速度
    public void SetRadiationSpeed(int s) {radiation_speed = s;}
    public int GetRadiationSpeed() {return radiation_speed;}
    private int radiation_number; // 一度に放射する弾の個数
    public void SetRadiationNumber(int rn) {radiation_number = rn;}
    public int GetRadiationNumber() {return radiation_number;}
    private int radiation_intvl; // 放射弾を発射する間隔
    public void SetRadiationIntvl(int ri) {radiation_intvl = ri;}
    public int GetRadiationIntvl() { return radiation_intvl;}
    private int angle_add; // 回転角
    public void SetAngleAdd(int aa) {angle_add = aa;}
    public int GetAngleAdd() {return angle_add;}
    private int howmany_added; // 何回角度を追加したか
    public void SetHowManyAdded(int hma) { howmany_added = hma;}
    public int GetHowManyAdded() { return howmany_added;}
    private int random_intvl; // ランダム弾の発射間隔
    public void SetRandomIntvl(int ri) { random_intvl = ri;}
    public int GetRandomIntvl() {return random_intvl;}
    private int random_speed; // ランダム弾の基本的速度
    public void SetRandomSpeed(int rs) {random_speed = rs;}
    public int GetRandomSpeed() {return random_speed;}
    private int random_range; // 乱数の範囲設定（角度の最大値】
    public void SetRandomRange(int rr) {random_range = rr;}
    public int GetRandomRange() {return random_range;}

    // フラグ
    private int isStateFinished; // 状態を切り替えるためのフラグ（状態開始時間セット等に役立つ）
    public void setIsStateFinished(int flag) { isStateFinished = flag;}
    public int IsStateFinished() {return isStateFinished;}

    // コンストラクタ
    public Boss(BossManager manager) {
        _bmanager = manager;
    }

    // 初期化
    public void init() {
        _effect = _bmanager.getSoundEffect();

        setIsStateFinished(0);
    }

    // 弾幕の初期化
    public BossBullet[] initBullet(BossBullet[] b) {
        return b;
    }

    // ボスの表示
    public void Show(Graphics2D g2) {

        if(!isEnable) return;
    }

    // ボスの動き
    public void Move() {
        super.Move();
    }

    // ----------ステート系のメソッド---------- //
    // ボスの出現ステート
    public void Appear() {}

    // ボスの戦闘ステート
    public void Battle() {}

    // 戦闘ステートの状態1
    public void BattleState1() {}

    // 戦闘ステートの状態2
    public void BattleState2() {}

    // 戦闘ステートの状態3
    public void BattleState3() {}

    // ボスが死亡するときのステート
    public void DeathState() {}

    // ------------ステート判定系メソッド--------------------//
    // 出現ステートか否かを判定する
    public boolean IsAppear() {
        if(getTime() < getBattleStartTime()) {return true;}
        return false;
    }

    // 状態1と状態2の間の時間か否かを判定する
    public boolean IsIntvl1And2() {
        if(getTime() >= getState1FinishTime() && getTime() < getBattleState2Start() ) {return true;}
        return false;
    }

    // 状態2と状態3の間の時間か否かを判定する
    public boolean IsIntvl2And3() {
        if(getTime() >= getState2FinishTime() && getTime() < getBattleState3Start()) {return true;}
        return false;
    }

    // 死亡ステートかを判定する
    public boolean IsDeath() {
        if(getHP() <= 0) {return true;}
        return false;
    }

    // -------弾幕系のメソッド------- //
    // 自機狙いの弾幕を生成
    public void CreateEimsBullet(int type)
    {
        Fighter fighter = _bmanager.GetFighter();
        BossBullet[] bullet = _bmanager.GetBossBullet();
        
        if(!isEnable) return;

        if(fY > 240) return;
        System.out.println("eimintvl = "+GetEimIntvl());//
        if((getTime() % GetEimIntvl()) == 0) {
            for(int i=0; i<GetBossBulletMax(); i++)
            {
                if(bullet[i].isEnable) continue;

                float dx, dy, d, speed;

                dx = fighter.GetX() - fX;
                dy = fighter.GetY() + 23 - fY;

                speed = getEimSpeed();

                d = (float)Math.sqrt(dx*dx + dy*dy);

                bullet[i].SetPos(fX, fY);
                bullet[i].SetVX((dx/d)*speed);
                bullet[i].SetVY((dy/d)*speed);
                bullet[i].setBossBulletType(type);

                bullet[i].Enable(true);

                break;
            }

            _bmanager.SetBossBullet(GetBossBullet());
        }
        
    }

    // 渦巻き形の弾幕を生成
    public void CreateRotateBullet(int type) {
        BossBullet[] bullet = _bmanager.GetBossBullet();

        if(!isEnable) return;

        if(fY > 240) return;
        if(getTime() % GetRotateIntvl() == 0) {
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].isEnable) {
                    continue;
                }
                float speed = GetRotateSpeed();

                SetCurrentAngle(GetCurrentAngle() + GetAngleAdjustX());
                bullet[i].SetPos(fX, fY);
                bullet[i].SetVX(speed * (float)Math.cos((float)Math.toRadians((float)(GetCurrentAngle() + GetAngleAdjustX()))));
                bullet[i].SetVY(speed * (float)Math.sin((float)Math.toRadians((float)(GetCurrentAngle() + GetAngleAdjustY()))));
                bullet[i].setBossBulletType(type);

                bullet[i].Enable(true);

                break;
            }
            _bmanager.SetBossBullet(GetBossBullet());
        }
    }

    // 花火弾幕を生成
    public void CreateFireWorksBullet(int type, int typeafter) {
        int i = 0, j;
        int isCreate = 0;
        BossBullet[] bullet = _bmanager.GetBossBullet();
        Fighter fighter = _bmanager.GetFighter();

        if(!isEnable) return;

        if(fY > 240) return;
        
        while(i < GetBossBulletMax()) {
            // 既に発射されているもの
            if(bullet[i].IsEnable()) {
                // 既に爆発していたら何もしない
                if(bullet[i].getIsExploded()) {++i; continue;}
                else {
                    // もし爆発させる時間になっていたら
                    int time = getTime() - bullet[i].getShootedTime();
                    if(time == GetShootExplodeIntvl()) {
                        // ピースの数だけ爆発させる
                        j = i + GetPieceNum();
                        while(i < j) {
                            float dx = bullet[i].GetVX(), dy = bullet[i].GetVY();
                            float d = (float)Math.sqrt(dx*dx + dy*dy);
                            bullet[i].setBossBulletType(typeafter);
                            bullet[i].SetVX((float)Math.cos(Math.acos(dx / d) + (360f * (j-i))) * GetVelocityTimesFirework() * d);
                            bullet[i].SetVY((float)Math.sin(Math.asin(dy / d) + (360f * (j-i))) * GetVelocityTimesFirework() * d);
                            bullet[i].setIsExploded(true);
                            ++i;
                        }
                    // まだ爆発させる時間でなければ
                    } else {
                        ++i; continue;
                    }
                }//
            } else {
                // まだ発射されていないもの
                if(isCreate == 0 && getTime() % GetFireworkShootIntvl() == 0 && i % GetPieceNum() == 0) {
                    j = i + GetPieceNum();
                    boolean isallfalse = false;
                    // ピースすべてがfalseでなければ作らない
                    if(i % GetPieceNum() == 0) {
                        while(i < j) {if(bullet[i].IsEnable()) isallfalse = true; ++i;}
                        if(isallfalse) continue;
                    }
                    i = j - GetPieceNum();
                    while(i < j) {
                        float dx, dy, d, speed;

                        dx = fighter.GetX() - fX;
                        dy = fighter.GetY() + 23 - fY;

                        speed = GetFireworkSpeed();

                        d = (float)Math.sqrt(dx*dx + dy*dy);

                        bullet[i].SetPos(fX, fY);
                        bullet[i].SetVX(0);
                        bullet[i].SetVY(speed);
                        bullet[i].setBossBulletType(type);
                        bullet[i].setIsExploded(false);
                        bullet[i].setShootedTime(getTime());
                        bullet[i].Enable(true);
                        ++i;
                    }
                    isCreate = 1;
                    continue;
                } else {
                    ++i; continue;
                }
            }
        }
        _bmanager.SetBossBullet(GetBossBullet());
    }

    // 渦巻き形の弾幕を生成
    public void CreateRadiationBullet(int type) {
        int j, k;
        BossBullet[] bullet = _bmanager.GetBossBullet();

        if(!isEnable) return;

        if(fY > 240) return;
        if(getTime() % GetRadiationIntvl() == 0) {
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].isEnable) {
                    continue;
                }

                float num = GetRadiationNumber();
                j = i; k = 0;
                int angle_add = GetAngleAdd();
                int howmany = GetHowManyAdded();
                int angle_add_true = angle_add * howmany;
                if(angle_add_true >= 360) {angle_add_true -= 360; howmany = angle_add_true / 360;}
                SetHowManyAdded(howmany + 1);

                while(k < num) {
                    if(bullet[j].isEnable) {++j; continue;}

                    float speed = GetRadiationSpeed();
                    float angle = (float)360 / (float)num;

                    bullet[j].SetPos(fX + (float)Math.cos(Math.toRadians((float)angle_add_true + (float)k * angle)), fY + (float)Math.sin(Math.toRadians((float)angle_add_true + (float)k * angle)));
                    bullet[j].SetVX(speed * (float)Math.cos(Math.toRadians((float)angle_add_true + (float)k * angle)));
                    bullet[j].SetVY(speed * (float)Math.sin(Math.toRadians((float)angle_add_true + (float)k * angle)));
                    bullet[j].setBossBulletType(type);

                    bullet[j].Enable(true);
                    ++j; ++k;
                }
                
                break;
            }
            _bmanager.SetBossBullet(GetBossBullet());
        }
    }

    // ランダム弾を生成
    public void CreateRandomBullet(int type)
    {
        BossBullet[] bullet = _bmanager.GetBossBullet();
        Random rand = new Random();
        int randomValue;
        
        if(!isEnable) return;

        if(fY > 240) return;
        if((getTime() % GetRandomIntvl()) == 0) {
            for(int i=0; i<GetBossBulletMax(); i++)
            {
                if(bullet[i].isEnable) continue;

                float speed = GetRandomSpeed();
                randomValue = rand.nextInt(GetRandomRange());

                bullet[i].SetPos(fX, fY);
                bullet[i].SetVX(speed * (float)Math.cos(180 + Math.toRadians(randomValue)));
                bullet[i].SetVY(speed * (float)Math.sin(-180 + Math.toRadians(randomValue)));
                bullet[i].setBossBulletType(type);

                bullet[i].Enable(true);

                break;
            }

            _bmanager.SetBossBullet(GetBossBullet());
        }
        
    }

    // ------------当たり判定系メソッド-------------- //
    // 弾幕と自機の当たり判定
    public boolean HitCheckBossBulletAndFighter() {
        if(!GetFighter().IsEnable()) return false;

		for(int i=0; i<GetBossBulletMax(); i++)
		{
			if(GetBossBullet()[i] == null || !GetBossBullet()[i].IsEnable()) continue;
				float dx, dy, width, height;
			dx = GetBossBullet()[i].GetX() - GetFighter().GetX();
			dy = GetBossBullet()[i].GetY() - GetFighter().GetY() - 23;

			width = 20;
			height = 28;

			// 当たった時
			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				// 自機がパワーダウンする効果音
				getSoundEffect().PlayPowerDown();
				
				GetBossBullet()[i].Enable(false);
				GetFighter().Enable(false);
				GetFighter().DecreasenLeft(1);
				GetFighter().SetMbegin(getTime());
				GetFighter().SetMuteki(1);
				return true;
			}
		}		
		return false;
    }

    // ボスと自機の弾の当たり判定
    public void HitCheckBossAndShot()
	{
        if(!IsAppear() && !IsIntvl1And2() && !IsIntvl2And3()) {
            for(int i=0;i<GetFighter().GetNumShot();i++)
            {
                // 弾が有効でないとき、飛ばす
                if(!GetFighter().GetShot()[i].IsEnable()) continue;

                if(!IsEnable()) continue;
                float dx, dy, width, height;
                dx = GetX() - GetFighter().GetShot()[i].GetX() - 5;
                dy = GetY() - GetFighter().GetShot()[i].GetY();

                width = 50;
                height = 60;

                if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
                {
                    
                    int n = GetFighter().GetShift();
                    if(n == 2){
                        setHP(getHP() - 2);
                    }else{
                        setHP(getHP() - 1);
                        System.out.println("HP = "+getHP());//
                    }
                    GetFighter().GetShot()[i].Enable(false);

                    // ボスに弾が当たる音を出す
                    getSoundEffect().PlayShotHitEnemy();
                    
                    break;
                }
            }
        }
	}
    
}


//------------------------------------------------------------

// ステージ1のボスクラス
class Boss1 extends Boss {

    // Warningのフォント
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // ボス1の弾幕クラス（内部クラス、BossBulletの継承）
    class BossBullet1 extends BossBullet {
        // 弾タイプ
        int type;
        // コンストラクタ
        public BossBullet1() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // 弾タイプによって場合分け
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.blue);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // ボス1のコンストラクタ
    public Boss1(BossManager manager) {
        super(manager);
    }

    // 弾タイプの初期化
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet1();
        }
        return b;
    }

    // 初期化
    @Override
    public void init() {
        super.init();

        // 座標や速度の設定
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // スタート時間の設定
        // 時間関連の設定
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HPの設定
        setHP(100);
        setHP_MAX(100);

        // 弾幕関連の設定
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetAngleAdjust(30, 30);
        SetPieceNum(5);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(1);
        SetShootExplodeIntvl(20);
        SetVelocityTimesFirework(2);

        // 文字列関連の設定("Warning"など)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // 弾幕のオブジェクトの設定
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // 自機のオブジェクトの設定
        SetFighter(_bmanager.GetFighter());
    }

    // ボス1の描画
    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HPバー
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("ボスHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }
        
        // 死亡ステートでないとき、若しくは死亡ステートで爆発する前に、ボスを描画する
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss1.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // 出現ステートにおいて、Warningと表示
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // 死亡ステートにて、爆発モーション表示
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    // ボス1の動き
    @Override
    public void Move() {
        super.Move();
    }

    // ----------- 弾幕系メソッド ------------ //
    // 自機狙いの弾を生成
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // 渦巻き弾幕の生成
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // 花火弾幕の生成
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    // ----------- ステート系メソッド ---------- //
    // 出現ステート
    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    // 戦闘ステート
    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        } else if(getHP() <= 0) {
            DeathState();
        }
    }

    // ボス1の状態1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateEimsBullet(0);
    }

    // ボス1の状態2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // 状態1が終わったばかりの時
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // 最初は正の方向に動く
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateFireWorksBullet(1, 4);
        }
    }

    // ボス1の状態3
    @Override
    public void BattleState3() {
        // 状態2が終わったばかりの時
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // 最初は正の方向に動く
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRotateBullet(2);
        }
    }

    // ボスが死亡するときのステート
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // ボスが止まる
            SetVX(0); SetVY(0);
            // 弾をすべて消す
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // 死亡ステート開始のフラグ
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}


// ----------------------------------------------------------------------------------------

// ステージ2のボスクラス
class Boss2 extends Boss {
    // Warningのフォント
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // ボス2の弾幕クラス（内部クラス、BossBulletの継承）
    class BossBullet2 extends BossBullet {
        // 弾タイプ
        int type;
        // コンストラクタ
        public BossBullet2() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // 弾タイプによって場合分け
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.blue);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 5:
                    g2.setPaint(Color.orange);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // コンストラクタ
    public Boss2(BossManager manager) {
        super(manager);
    }

    // 弾タイプの初期化
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet2();
        }
        return b;
    }

    @Override
    public void init() {
        super.init();

        // 座標や速度の設定
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // スタート時間の設定
        // 時間関連の設定
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HPの設定
        setHP(100);
        setHP_MAX(100);

        // 弾幕関連の設定
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetAngleAdjust(1,1);
        SetPieceNum(8);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(15);
        SetShootExplodeIntvl(15);
        SetVelocityTimesFirework(2);
        SetRadiationIntvl(15);
        SetRadiationNumber(20);
        SetRadiationSpeed(15);
        SetAngleAdd(15);
        SetHowManyAdded(0);
        SetRandomIntvl(5);
        SetRandomSpeed(15);
        SetRandomRange(60);

        // 文字列関連の設定("Warning"など)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // 弾幕のオブジェクトの設定
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // 自機のオブジェクトの設定
        SetFighter(_bmanager.GetFighter());
    }

    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HPバー
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("ボスHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }

        // 死亡ステートでないとき、若しくは死亡ステートで爆発する前に、ボスを描画する
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss2.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // 出現ステートにおいて、Warningと表示
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.yellow);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // 出現ステートにおいて、Warningと表示
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // 死亡ステートにて、爆発モーション表示
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    @Override
    public void Move() {
        super.Move();
    }

        // ----------- 弾幕系メソッド ------------ //
    // 自機狙いの弾を生成
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // 渦巻き弾幕の生成
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // 花火弾幕の生成
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    @Override
    public void CreateRadiationBullet(int type) {
        super.CreateRadiationBullet(type);
    }

    // ----------- ステート系メソッド ---------- //

    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        }
        if(getHP() <= 0) {DeathState();}
    }

    // ボス2の状態1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateFireWorksBullet(1, 5);
    }

    // ボス2の状態2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // 状態1が終わったばかりの時
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(3);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // 最初は正の方向に動く
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateRadiationBullet(4);
        }
    }

    // ボス2の状態3
    @Override
    public void BattleState3() {
        // 状態2が終わったばかりの時
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // 最初は正の方向に動く
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRandomBullet(0);
            
        }
    }

    // ボスが死亡するときのステート
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // ボスが止まる
            SetVX(0); SetVY(0);
            // 弾をすべて消す
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // 死亡ステート開始のフラグ
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}


// ----------------------------------------------------------------------------------


// ステージ3のボスクラス
class Boss3 extends Boss {
    // Warningのフォント
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // ボス3の弾幕クラス（内部クラス、BossBulletの継承）
    class BossBullet3 extends BossBullet {
        // 弾タイプ
        int type;
        // コンストラクタ
        public BossBullet3() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // 弾タイプによって場合分け
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.magenta);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // コンストラクタ
    public Boss3(BossManager manager) {
        super(manager);
    }

    // 弾タイプの初期化
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet3();
        }
        return b;
    }

    @Override
    public void init() {
        super.init();

        // 座標や速度の設定
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // スタート時間の設定
        // 時間関連の設定
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HPの設定
        setHP(100);
        setHP_MAX(100);

        // 弾幕関連の設定
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetCurrentAngle(0);
        SetAngleAdjust(30, 30);
        SetPieceNum(8);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(60);
        SetShootExplodeIntvl(15);
        SetVelocityTimesFirework(2);
        SetRadiationIntvl(15);
        SetRadiationNumber(20);
        SetRadiationSpeed(15);
        SetAngleAdd(15);
        SetHowManyAdded(0);
        SetRandomIntvl(1);
        SetRandomSpeed(15);
        SetRandomRange(60);

        // 文字列関連の設定("Warning"など)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // 弾幕のオブジェクトの設定
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // 自機のオブジェクトの設定
        SetFighter(_bmanager.GetFighter());
    }

    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HPバー
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("ボスHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }

        // 死亡ステートでないとき、若しくは死亡ステートで爆発する前に、ボスを描画する
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss3.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // 出現ステートにおいて、Warningと表示
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.yellow);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // 出現ステートにおいて、Warningと表示
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // 死亡ステートにて、爆発モーション表示
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    @Override
    public void Move() {
        super.Move();
    }

        // ----------- 弾幕系メソッド ------------ //
    // 自機狙いの弾を生成
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // 渦巻き弾幕の生成
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // 花火弾幕の生成
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    @Override
    public void CreateRadiationBullet(int type) {
        super.CreateRadiationBullet(type);
    }

    // ----------- ステート系メソッド ---------- //

    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        }
        if(getHP() <= 0) {DeathState();}
    }

    // ボス3の状態1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateRotateBullet(1);
    }

    // ボス3の状態2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // 状態1が終わったばかりの時
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(3);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // 最初は正の方向に動く
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateRadiationBullet(3);
        }
    }

    // ボス3の状態3
    @Override
    public void BattleState3() {
        // 状態2が終わったばかりの時
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // 最初は正の方向に動く
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRandomBullet(2);
            CreateRadiationBullet(3);
            
        }
    }

    // ボスが死亡するときのステート
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // ボスが止まる
            SetVX(0); SetVY(0);
            // 弾をすべて消す
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // 死亡ステート開始のフラグ
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}


// ----------------------------------------------------------------------------------------------------------

// ステージ4のボスクラス
class Boss4 extends Boss {
    // Warningのフォント
    private Font warfont = new Font("Arial", Font.BOLD, 40);

    // ボス4の弾幕クラス（内部クラス、BossBulletの継承）
    class BossBullet4 extends BossBullet {
        // 弾タイプ
        int type;
        // コンストラクタ
        public BossBullet4() {
            super();
        }

        @Override
        public void Show(Graphics2D g2) {
            if(!isEnable) return;

            // 弾タイプによって場合分け
            switch(getBossBulletType()) {
                case 0:
                    g2.setPaint(Color.red);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 1:
                    g2.setPaint(Color.YELLOW);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 2:
                    g2.setPaint(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 3:
                    g2.setPaint(Color.magenta);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                case 4:
                    g2.setPaint(Color.pink);
                    g2.fill(new Ellipse2D.Double(fX-5f, fY-5f, 10f, 10f));
                    break;
                default:
                    break;
            }
        }
    }

    // コンストラクタ
    public Boss4(BossManager manager) {
        super(manager);
    }

    // 弾タイプの初期化
    @Override
    public BossBullet[] initBullet(BossBullet[] b) {
        for(int i = 0; i < GetBossBulletMax(); ++i) {
            b[i] = new BossBullet4();
        }
        return b;
    }

    @Override
    public void init() {
        super.init();

        // 座標や速度の設定
        SetPos(240f, 0f);
        SetVX(0f); SetVY(1f);
        setVAppear(0f, 2f);
        setStartTime(_bmanager.GetTime());
        Enable(true);
        
        // スタート時間の設定
        // 時間関連の設定
        setStartTime(_bmanager.GetMain().GetTime());
        setAppearSpan(60);
        setAppearBattleIntvl(30);
        setAppearFinishTime(getStartTime() + getAppearSpan());
        setBattleStartTime(getAppearFinishTime() + getAppearBattleIntvl());
        setState1and2Intvl(70);
        setState2and3Intvl(70);
        setBossWinkIntvl(4);
        setBossWinkSpan(70);
        setScreenWinkIntvl(2);
        setScreenWinkSpan(10);
        setWinkFinishSpan(30);
        setClearBGMSpan(70);

        // HPの設定
        setHP(100);
        setHP_MAX(100);

        // 弾幕関連の設定
        SetBossBulletMax(300);
        setEimSpeed(20);
        SetEimIntvl(8);
        SetRotateIntvl(1);
        SetRotateSpeed(20);
        SetCurrentAngle(0);
        SetAngleAdjust(30, 30);
        SetPieceNum(8);
        SetFireworkSpeed(10);
        SetFireworkShootIntvl(5);
        SetShootExplodeIntvl(10);
        SetVelocityTimesFirework(2);
        SetRadiationIntvl(10);
        SetRadiationNumber(20);
        SetRadiationSpeed(10);
        SetAngleAdd(15);
        SetHowManyAdded(0);
        SetRandomIntvl(1);
        SetRandomSpeed(15);
        SetRandomRange(60);

        // 文字列関連の設定("Warning"など)
        setAppearStringIntvl(10);
        setAppearStringPos(100, 250);

        // 弾幕のオブジェクトの設定
        SetBossBullet(new BossBullet[GetBossBulletMax()]);
        SetBossBullet(initBullet(GetBossBullet()));
        _bmanager.SetBossBullet(GetBossBullet());
        _bmanager.SetBossBulletMax(GetBossBulletMax());

        // 自機のオブジェクトの設定
        SetFighter(_bmanager.GetFighter());
    }

    @Override
    public void Show(Graphics2D g2) {
        super.Show(g2);

        // HPバー
        if(!IsAppear() && !IsDeath()) {
            g2.setPaint(Color.white);
            g2.drawString("ボスHP", 20, 20);
            float rate = (float)getHP() / (float)getHP_MAX();
            g2.setPaint(new Color((int)(255f - 255f * rate), (int)(0f + 255f * rate), 0));
            g2.fillRect(20, 40, (int)Math.ceil(440f * rate), 10);
        }

        // 死亡ステートでないとき、若しくは死亡ステートで爆発する前に、ボスを描画する
        if(!IsDeath() || (IsDeath() && (getTime() <= getDeathStateStart() + getBossWinkSpan()))) {
            if(IsDeath() && (getTime() % getBossWinkIntvl() <= getBossWinkIntvl() / 2)) {
                // do nothing
            } else {
                Image img = Toolkit.getDefaultToolkit().getImage("Boss1.png");
                g2.drawImage(img, new AffineTransform(1f,0f,0f,1f,fX-img.getWidth(null)/2,fY-img.getHeight(null)/2), null);
            }
        }

        // 出現ステートにおいて、Warningと表示
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.yellow);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // 出現ステートにおいて、Warningと表示
        if(IsAppear()) {
            int period = getTime() % getAppearBattleIntvl();
            float half = getAppearBattleIntvl() / 2;
            if(period <= half) {
                g2.setFont(warfont);
                g2.setPaint(Color.RED);
                g2.drawString("Warning!", 145, 250);
            }
        }

        // 死亡ステートにて、爆発モーション表示
        if(IsDeath() && getTime() >= getDeathStateStart() + getBossWinkSpan() && getTime() < getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan()) {
            if(getTime() % getScreenWinkIntvl() == getScreenWinkIntvl() / 2) {
                g2.setPaint(Color.white);
                g2.fillRect(0, 0, 480, 640);
            }
        }
    }

    @Override
    public void Move() {
        super.Move();
    }

        // ----------- 弾幕系メソッド ------------ //
    // 自機狙いの弾を生成
    @Override
    public void CreateEimsBullet(int type) {
        super.CreateEimsBullet(type);
    }
    
    // 渦巻き弾幕の生成
    @Override
    public void CreateRotateBullet(int type) {
        super.CreateRotateBullet(type);
    }

    // 花火弾幕の生成
    @Override
    public void CreateFireWorksBullet(int type, int typeafter) {
        super.CreateFireWorksBullet(type, typeafter);
    }

    @Override
    public void CreateRadiationBullet(int type) {
        super.CreateRadiationBullet(type);
    }

    // ----------- ステート系メソッド ---------- //

    @Override
    public void Appear() {
        super.Appear();
        if(getTime() < getAppearFinishTime()) {
            SetVX(getVXAppear()); SetVY(getVYAppear());
        } else if(getTime() >= getAppearFinishTime() && getTime() < getBattleStartTime()) {
            SetVX(0); SetVY(0); setState1StartPos(GetX(), GetY());
        }
    }

    @Override
    public void Battle() {
        super.Battle();
        if(getTime() >= getBattleStartTime() && getHP()>= getHP_MAX()* 2 / 3) {
            BattleState1();
        } else if(getHP() < (getHP_MAX()* 2 / 3) && getHP() >= (getHP_MAX() / 3)) {
            BattleState2();
        } else if(getHP() > 0 && getHP() < (getHP_MAX() / 3)) {
            BattleState3();
        }
        if(getHP() <= 0) {DeathState();}
    }

    // ボス4の状態1
    @Override
    public void BattleState1() {
        if(GetVX() == 0 && GetVY() == 0) SetVX(10); SetVY(0);
        if(fX <= 0 && GetVX() < 0) {
            SetVX(10); SetVY(0);
        } else if(fX >= 430 && GetVX() > 0) {
            SetVX(-10); SetVY(0);
        }
        CreateEimsBullet(2);
    }

    // ボス4の状態2
    @Override
    public void BattleState2() {
        Random rand = new Random();
        // 状態1が終わったばかりの時
        if(IsStateFinished() == 0) {
            setState1FinishTime(getTime());
            setBattleState2Start(getState1FinishTime() + getState1and2Intvl());
            setIsStateFinished(3);
        }
        if(IsIntvl1And2()) {
            setState2StartPos(getState1StartX(), getState1StartY());
            SetPos(getState1StartX(), getState1StartY());
        } else {
            // 最初は正の方向に動く
            if((IsStateFinished() == 1 && fX == getState2StartX()) || fX <= 0) {
                setIsStateFinished(2);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            }
            CreateFireWorksBullet(2, 4);
        }
    }

    // ボス4の状態3
    @Override
    public void BattleState3() {
        // 状態2が終わったばかりの時
        if(IsStateFinished() == 2) {
            setState2FinishTime(getTime());
            setBattleState3Start(getState2FinishTime() + getState2and3Intvl());
            setIsStateFinished(1);
        }
        if(IsIntvl2And3()) {
            setState3StartPos(getState2StartX(), getState2StartY());
            SetPos(getState2StartX(), getState2StartY());
        } else {
            // 最初は正の方向に動く
            if(IsStateFinished() == 1 || fX <= 0) {
                setIsStateFinished(0);
                SetVX(30);
            } else if(fX >= 440) {
                SetVX(-30);
            } else {
                // do nothing
            }
            CreateRandomBullet(0);
        }
    }

    // ボスが死亡するときのステート
    @Override
    public void DeathState() {
        BossBullet[] bullet;
        if(IsStateFinished() == 0) {
            setIsStateFinished(1);
            setState3FinishTime(getTime());
            setDeathStateStart(getTime());
            // ボスが止まる
            SetVX(0); SetVY(0);
            // 弾をすべて消す
            bullet = _bmanager.GetBossBullet();
            for(int i = 0; i < GetBossBulletMax(); ++i) {
                if(bullet[i].IsEnable()) {bullet[i].Enable(false);}
            }
            // 死亡ステート開始のフラグ
            _bmanager.SetIsDead(true);
        } else {
            int time = getDeathStateStart() + getBossWinkSpan() + getScreenWinkSpan() + getWinkFinishSpan();
            if(getTime() >= time - getScreenWinkSpan() - getWinkFinishSpan() && getTime() < time - getWinkFinishSpan()) {
                if(getTime() % getScreenWinkIntvl() == 0) {
                    getSoundEffect().PlayBomb();
                }
            }
            if(getTime() == time) {
                if(!_bmanager.IsDeadState()) {_bmanager.SetIsDeadState(true);}
            } else if(getTime() > time + getClearBGMSpan()){
                _bmanager.SetIsClearBGM(true);
            }
        }
    }
}