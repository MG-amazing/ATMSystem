package ATM;

/**
 * 账户类
 * 创建一个账户类
 * 将类中的对象进行封装
 * 提供get和set方法来获取数据
 */
public class Account {
private String cardId;//卡号
private String userName;//用户名
private String passWord;//密码
private double money;//金额

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }

    private double quotaMoney;//金额最大额度
}
