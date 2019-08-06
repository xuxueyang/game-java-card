package itemmanager.dto;

public class AdminUpdateEnvoyDTO {
    private Long id;
    private Integer attribute;//属性(code)
    private Integer race;//种族(code)

    private String name;//名字

    private String icon;//头像

    private Integer grade;//品级

    private Integer attack;//攻击力
    private Integer incrAttack;//成长攻击力

    private Integer defense;//防御
    private Integer incrDefense;//成长防御
    private Integer hp;//血量
    private Integer incrHp;//成长血量
    private Integer move;//移动力
    private Integer attackDistance;//攻击距离

    private String description;

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getRace() {
        return race;
    }

    public void setRace(Integer race) {
        this.race = race;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
