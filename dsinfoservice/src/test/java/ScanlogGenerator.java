import aa.app.log.UserscanLog;
import aa.app.utils.UrlsendUtil;
import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ScanlogGenerator {

    private static Long[] pindaoids = new Long[]{1L,2L,3L,4L,5L,6L,7L, 8L};
    private static Long[] leibieids = new Long[]{1L,2L,3L,4L,5L,6L,7L, 8L};
    private static Long[] chanpinids = new Long[]{1L,2L,3L,4L,5L,6L,7L, 8L};
    private static Long[] yonghuids = new Long[]{1L,2L,3L,4L,5L,6L,7L, 8L};

    private static String[] countries = new String[]{"美国","中国","俄罗斯","泰国","老挝","缅甸"};
    private static String[] provinces = new String[]{"纽约","河北","莫斯科","曼谷","万象","内比都"};
    private static String[] citys = new String[]{"美国","石家庄","俄罗斯","泰国","老挝","缅甸"};

    private static String[] networks = new String[]{"电信","移动","联通"};

    private static String[] sources = new String[]{"直接输入","百度","360","比应"};
    private static String[] browsers = new String[]{"火狐","谷歌","QQ","搜狗"};

    private static List<Long[]> usertimelog = new ScanlogGenerator().producetimes();

    public List<Long[]> producetimes(){
        List<Long[]> usertimelog = new ArrayList<>();
        for(int i = 0;i<10;i++){
            Long[] timesarray = gettimes("2019-01-01 12:23:23:111");
            usertimelog.add(timesarray);
        }
        return  usertimelog;
    }

    private Long[] gettimes(String time){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        try {
            Date date = dateFormat.parse(time);
            long timestemp = date.getTime();
            Random random = new Random();
            int randomint = random.nextInt(10);
            long starttime = timestemp - randomint*3600*1000;
            long endtime = starttime + randomint*3600*1000;
            return  new Long[]{starttime,endtime};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Long[]{0L,0L};
    }

    public static void main(String[] args) {
        Random random = new Random();
        UserscanLog userscanLog = new UserscanLog();
        userscanLog.setPingdaoid(pindaoids[random.nextInt(pindaoids.length)]);
        userscanLog.setLeibieid(leibieids[random.nextInt(leibieids.length)]);
        userscanLog.setChanpinid(chanpinids[random.nextInt(chanpinids.length)]);
        userscanLog.setYonghuid(yonghuids[random.nextInt(yonghuids.length)]);

        userscanLog.setCountry(countries[random.nextInt(countries.length)]);
        userscanLog.setProvince(provinces[random.nextInt(provinces.length)]);
        userscanLog.setCity(citys[random.nextInt(citys.length)]);

        userscanLog.setNetwork(networks[random.nextInt(networks.length)]);
        userscanLog.setSource((sources[random.nextInt(sources.length)]));

        userscanLog.setBrowser(browsers[random.nextInt(browsers.length)]);
        Long[] times = usertimelog.get(random.nextInt(usertimelog.size()));
        userscanLog.setStarttime(times[0]);
        userscanLog.setStarttime(times[1]);

        String jsonstr = JSONObject.toJSONString(userscanLog);
        System.out.println("jsonstr==========>" + jsonstr);
        String address="http://localhost:6097/dsInfoController/webInfoController";
        UrlsendUtil.sendmessage(address,jsonstr);

    }

}
