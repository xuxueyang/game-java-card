package core.dto.item.dto;

public class EnvoyDTO {
    private Long id;
    private String attributeName;//属性(code)

    private String raceName;//种族名字(code)

    private String name;//名字

    private String icon;//头像
    private String gradeName;//品级

    private Integer starForce;//星辰值

    private Integer maxPlusStarForce=20;//最大的星辰值

    private Integer attack;//攻击力
    private Integer incrAttack;//成长攻击力

    private Integer defense;//防御
    private Integer incrDefense;//成长防御
    private Integer hp;//血量
    private Integer incrHp;//成长血量
    private Integer move;//移动力
    private Integer attackDistance;//攻击距离

    private Integer criticalRate = 10;//暴击率

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getStarForce() {
        return starForce;
    }

    public void setStarForce(Integer starForce) {
        this.starForce = starForce;
    }

    public Integer getMaxPlusStarForce() {
        return maxPlusStarForce;
    }

    public void setMaxPlusStarForce(Integer maxPlusStarForce) {
        this.maxPlusStarForce = maxPlusStarForce;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getIncrAttack() {
        return incrAttack;
    }

    public void setIncrAttack(Integer incrAttack) {
        this.incrAttack = incrAttack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getIncrDefense() {
        return incrDefense;
    }

    public void setIncrDefense(Integer incrDefense) {
        this.incrDefense = incrDefense;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getIncrHp() {
        return incrHp;
    }

    public void setIncrHp(Integer incrHp) {
        this.incrHp = incrHp;
    }

    public Integer getMove() {
        return move;
    }

    public void setMove(Integer move) {
        this.move = move;
    }

    public Integer getAttackDistance() {
        return attackDistance;
    }

    public void setAttackDistance(Integer attackDistance) {
        this.attackDistance = attackDistance;
    }

    public Integer getCriticalRate() {
        return criticalRate;
    }

    public void setCriticalRate(Integer criticalRate) {
        this.criticalRate = criticalRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }
}
