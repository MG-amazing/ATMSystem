package ATM;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ATM的入口
 */
public class ATMSystem {
    public static void main(String[] args) {
        //定义账户类
        //定义容器，存储账户对象，进行相关的业务操作。
        Scanner sc=new Scanner(System.in);
        ArrayList<Account>accounts=new ArrayList<>();//定义一个ArrayList容器
        //展示首页
        //控制台首页必须是一个死循环
         while (true) {//套死循环
            System.out.println("===============ATM系统=================");
            System.out.println("1、账户登录");
            System.out.println("2、账户开户");
            System.out.println("请您选择操作：");
            int command =sc.nextInt();
            switch (command){
                case 1:
                    //用户登录
                    login(accounts,sc);
                    break;
                case 2:
                    //用户开户
                    //最先开发开户功能
                    register(accounts,sc);//创建注册功能并用idea自带的提示方案创建注册功能并将accounts 和 Scanner sc 传入方法中节省内存
                    break;
                default://用户输入其他值，提示用户命令不存在！！！
                    System.out.println("您输入的命令不存在！！！");
            }
        }
    }

    /**
     * 登录功能
     * 全部账户对象的集合
     * @param accounts 所有账户的集合
     * @param sc 扫描器
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("===================系统登录======================");
    //判断账户中是否存在账户，如果不存在账户，登录不能进行
        if (accounts.size()==0){
            System.out.println("对不起，系统中没有账户，请先开户");
            return;//进行解决方案
        }
        while (true) {
            System.out.println("请您输入登录卡号：");
            String cardId = sc.next();
            //判断卡号是否存在，根据卡号查用户对象。
            Account acc=getAccountByCardId(cardId,accounts);
            if (acc!=null){
                //卡号存在
                //用户输入密码验证密码
                System.out.println("请您输入登录密码：");
                String passWord=sc.next();
                if (acc.getPassWord().equals(passWord)){
                    //登录成功
                    System.out.println("恭喜您，"+acc.getUserName()+"先生/女士进入系统，您的卡号是"+acc.getCardId());
                    //查询 转账 取款
                    //展示登录后的操作页
                    showUserCommand(sc,acc,accounts);
                    return;//干掉登录方法
                }else {
                    System.out.println("对不起，您输入的密码有误！！！");
                }
            }else {
                System.out.println("对不起，系统中不存在此账号！！！");
            }
        }
    }

    /**
     * 用户操作页
     * @param sc 扫描器
     * @param acc 当前账户对象
     * @param accounts 所有账户的集合
     */
    private static void showUserCommand(Scanner sc,Account acc,ArrayList accounts) {
        while (true) {
            System.out.println("==============用户操作页================");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、修改密码");
            System.out.println("6、退出");
            System.out.println("7、注销账户");
            System.out.println("请选择：");
            int command=sc.nextInt();
            switch (command){
                case 1:
                    //查询账户
                    showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(acc,sc);
                    break;
                case 3:
                    //取款
                    drawMoney(acc,sc);
                    break;
                case 4:
                    //转账
                    transferMoney(sc,acc,accounts);
                    break;
                case 5:
                    //修改密码
                    updatePassWord(sc,acc);
                    return;//让用户回到首页重新登录
                case 6:
                    //退出
                    System.out.println("退出成功，欢迎下次光临");
                    return;//让当前方法停止
                case 7:
                    //注销账户
                    //从当前账户集合中删除账户
                   deleteAccount(acc,accounts,sc);
                return;
                default:
                    System.out.println("当前命令不存在");
            }
        }
    }

    /**
     * 销户功能
     * @param acc 当前账户对象
     * @param accounts 所有账户的集合
     * @param sc 扫描器
     */
    private static void deleteAccount(Account acc, ArrayList accounts, Scanner sc) {
        System.out.println("===============用户销户====================");
        System.out.println("您真的要销户？？？输入“y”确认销户");
        String rs=sc.next();
        switch (rs){
            case "y":
                if (acc.getMoney()>0){
                    System.out.println("您的账户中还有钱，不能销户");
                }else {
                    accounts.remove(acc);
                    System.out.println("销户完毕");
                }
                break;
            default:
                System.out.println("当前账户继续保留!!");
        }
    }

    /**
     * 修改密码
     * @param sc 扫描器
     * @param acc 当前账户对象
     */
    private static void updatePassWord(Scanner sc, Account acc) {
        System.out.println("=================用户密码修改===================");
        while (true) {
            System.out.println("请输入当前密码：");
            String passWord=sc.next();
                if (acc.getPassWord().equals(passWord)){
                    while (true) {
                        //密码正确
                        //输入新密码
                        System.out.println("请您输入新密码:");
                        String newPassword=sc.next();
                        System.out.println("请您确认密码");
                        String okPassword=sc.next();
                        if (newPassword.equals(okPassword)){
                            acc.setPassWord(okPassword);
                            System.out.println("您的密码已更新");
                            return;//干掉方法
                        }else {
                            System.out.println("您两次输入的密码不一致");
                        }
                    }
                }else {
                System.out.println("您输入的密码不正确~");
            }
        }
    }

    /**
     * 转账功能
     * @param sc 扫描器
     * @param acc   自己的账户对象
     * @param accounts 全部账户的集合
     */
    private static void transferMoney(Scanner sc, Account acc, ArrayList accounts) {
        System.out.println("==================用户转账操作======================");
    if (accounts.size()<2){
        System.out.println("当前系统中不存在2个账户，无法进行转账，请先开户");
        return;
        }
    if (acc.getMoney()==0){
        System.out.println("对不起您，自己没钱，请先去充值");
        return;
    }
        while (true) {
            System.out.println("请您输入对方账户的卡号：");
            String cardId =sc.next();

            //这和卡号不能是自己的卡号
            if (cardId.equals(acc.getCardId())){
                System.out.println("您输入的卡号是您自己的卡号");
                continue;//结束执行，死循环进入下一次
            }
            //判断这个卡号是否存在,根据这个考好查询对方账户对象。
            Account account=getAccountByCardId(cardId,accounts);
            if (account==null){
                System.out.println("对不起，您输入的卡号不存在！！");
            }else {
                    //认证对方姓氏
                String userName =account.getUserName();
                //截取字符串
                String tip ="-"+userName.substring(1);
                System.out.println("请您输入["+tip+"]的姓氏");
                String preName =sc.next();
                //认证姓氏是否输入正确
                if (userName.startsWith(preName)){
                    while (true) {
                        //认证通过，真正开始转账了
                        System.out.println("请您输入转账金额");
                        double money=sc.nextDouble();
                        //判断金额是否足够
                        if (money>acc.getMoney()){
                            System.out.println("对不起，您的余额不足，您最多可以转账："+acc.getMoney());
                        }else {//余额足够可以转账
                            acc.setMoney((acc.getMoney()-money));
                            account.setMoney(account.getMoney()+money);
                            System.out.println("转账成功！您的账户还剩余"+acc.getMoney());
                            return;//干掉转账方法
                        }
                    }
                }else {
                    System.out.println("对不起，您输入的信息有误！！");
                }
            }
        }
    }
    /**
     * 取钱功能
     * @param acc 当前账户对象
     * @param sc  扫描器
     */
    private static void drawMoney(Account acc, Scanner sc) {
        System.out.println("================用户取钱操作=================");
    //判断是否小于100块
        if (acc.getMoney()<100){
            System.out.println("用户中金额不足100元，无法取出");
            return;
        }
        System.out.println("请输入取款金额：");
        double money=sc.nextDouble();

        if (money>acc.getQuotaMoney()){
            System.out.println("对不起，您输入的金额超过每次限额，每次最多可取"+acc.getQuotaMoney());
        }else {


            if (money>acc.getMoney()){
                System.out.println("余额不足，您账户中总余额是："+acc.getMoney());
            }else {
                //可以取钱
                System.out.println("恭喜您取钱成功,取钱"+money+"元");
                acc.setMoney(acc.getMoney()-money);
                showAccount(acc);
                return;//干掉取钱方法
            }
        }
    }

    /**
     * 存钱
     * @param acc 当前账户对象
     * @param sc 扫描器
     */

    private static void depositMoney(Account acc,Scanner sc) {
        System.out.println("==============用户存款操作===============");
        System.out.println("请您输入存款金额：");
        double money =sc.nextDouble();
        //更新账户余额：原来的钱+新存入的钱
        acc.setMoney(acc.getMoney()+money);
        System.out.println("恭喜你存钱成功，当前账户信息如下：");
        showAccount(acc);
    }

    /**
     * 展示账户信息
     * @param acc 当前用户对象
     */
    private static void showAccount(Account acc) {
        System.out.println("===============当前账户信息如下=============");
        System.out.println("卡号:"+acc.getCardId());
        System.out.println("户主:"+acc.getUserName());
        System.out.println("余额:"+acc.getMoney());
        System.out.println("限额:"+acc.getQuotaMoney());
    }

    /**
     * 开户功能
     * @param accounts 全部账户集合
     * @param sc 扫描器
     */

    private static void register(ArrayList<Account> accounts,Scanner sc) {
        System.out.println("================系统开户操作=================");
    //创建一个账户对象，用于封装用户信息。
        Account account=new Account();
    //录入当前账户的信息，并注入到账户对象中去。
        System.out.println("请输入账户的用户名：");
        String userName = sc.next();
        account.setUserName(userName);
        while (true) {
            System.out.println("请输入账户密码：");
            String password =sc.next();
            System.out.println("请您确认您输入的密码:");
            String okPassword =sc.next();
            if (okPassword.equals(password)){
                //密码认证通过
                account.setPassWord(okPassword);
                break;//结束死循环
            }else {
                System.out.println("您两次输入的密码不一致！！！");
            }
        }
        System.out.println("请您输入当次限额：");
        double quotaMoney =sc.nextDouble();
        account.setQuotaMoney(quotaMoney);
        //为账户随机生成一个8位且与其他账户卡号不一致的号码
        String cardId=getRandomCardId(accounts);
        account.setCardId(cardId);
        //把账户对象添加到集合中去
        accounts.add(account);//开户完成
        System.out.println("恭喜您"+userName+"先生/女士,您开户成功您的卡号"+cardId+"请您妥善保管");
    }

    /**
     * 为账户生成8位与其他用户卡号不同的号码。
     * @param accounts 所有账户集合
     * @return 干掉方法
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random r=new Random();
        while (true) {
            String cardId="";
            for (int i = 0; i <8 ; i++) {
                cardId+=r.nextInt(10);
            }
            //判断这8位的卡号是否与其他账户的卡号重复。
            Account acc=getAccountByCardId(cardId,accounts);
            if (acc==null){

            }

            return cardId;
        }

    }

    /**
     *  根据卡号查询除一个账户对象出来
     * @param cardId 银行卡号
     * @param accounts 所有账户的集合
     * @return 返回值
     */

    private static Account getAccountByCardId(String cardId,ArrayList<Account>accounts){

        for (int i = 0; i <accounts.size() ; i++) {
            Account acc=accounts.get(i);
            if (acc.getCardId().equals(cardId)){
                return acc;
            }
            else if (cardId.charAt(0)==0){
                return acc;
            }
        }
        return null;//查无此账户
    }
}