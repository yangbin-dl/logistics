package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.json.JsonData;
import com.mallfe.common.json.JsonError;
import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.AllBillMapper;
import com.mallfe.item.mapper.ConsumerMapper;
import com.mallfe.item.mapper.ThMapper;
import com.mallfe.item.mapper.XsMapper;
import com.mallfe.item.pojo.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * 销售退货Service
 * @author yangbin
 * @since 2019-07-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class XsThService {
    @Autowired
    XsMapper xsMapper;

    @Autowired
    ThMapper thMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    AllBillMapper allBillMapper;

    @Autowired
    ConsumerMapper consumerMapper;

    @Autowired
    UserService userService;


    public Xs insertXs(@NotNull Xs xs){
        String lsh = commonService.getLsh("XS");
        xs.setLsh(lsh);
        xs.setStatus(0);
        xs.setLrsj(CommonService.getStringDate());
        try {
            xsMapper.insert(xs);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        return xs;
    }

    public Th insertTh(@NotNull Th th){
        String lsh = commonService.getLsh("TH");
        th.setLsh(lsh);
        th.setStatus(0);
        th.setLrsj(CommonService.getStringDate());
        try {
            thMapper.insert(th);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        return th;
    }

    public Xs modifyXs(@NotNull Xs xs){
        try {
            if (xsMapper.updateBill(xs) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return xs;
    }

    public Th modifyTh(@NotNull Th th){
        try {
            if (thMapper.updateBill(th) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return th;
    }

    public PageResult<Xs> queryXsByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Xs> list = xsMapper.selectXsList(key,null);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Xs> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Th> queryThByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);

        List<Th> list = thMapper.selectThList(key,null);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Th> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public JsonObject commitXs(Xs xs){
        try {
            if(xsMapper.updateStatusToCommited(xs)!=1){
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");
    }

    public JsonObject deleteXs(Xs xs){
        try {
            if(xsMapper.updateStatusToCancel(xs)!=1){
                return new JsonError("单据状态异常，作废失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，作废失败！");
        }
        return new JsonData("作废成功！");
    }

    public JsonObject commitTh(Th th){
        try {
            if(thMapper.updateStatusToCommited(th)!=1){
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");
    }

    public JsonObject deleteTh(Th th){
        try {
            if(thMapper.updateStatusToCancel(th)!=1) {
                return new JsonError("单据状态异常，作废失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，作废失败！");
        }
        return new JsonData("作废成功！");
    }

    public AllBill queryBill(String lsh){

        AllBill bill = xsMapper.selectOneBill(lsh);

        return bill;
    }

    public JsonObject queryAll(Integer page, String lruserid, String phone, Integer hh, String lsh, String contact) {
        //分页
        PageHelper.startPage(page, 10);
        //条件过滤

        //查询用户角色
        List<AllBill> list;
        User u = userService.selectById(Integer.parseInt(lruserid));

        if(u.getLx()==2){
            list = xsMapper.selectAllBill(lruserid,phone,lsh,hh,contact);
        } else {
            list = xsMapper.selectAllBillByStore(u.getStoreCode(),phone,lsh,hh,contact);
        }

        //查询

        if(CollectionUtils.isEmpty(list)){
            return new JsonError("未查询到单据！");
        }

        //解析分页结果
        PageInfo<AllBill> info = new PageInfo<>(list);

        return new JsonData(new PageResult<>(info.getTotal(), list));
    }

    public JsonObject queryByPhone(String phone) {
        Consumer consumer = consumerMapper.queryByPhone(phone);
        return new JsonData(consumer);
    }

    public List<Xs> queryXsForPs(String lsh) {
        List<Xs> list;

        if(StringUtils.isNotBlank(lsh)){
            list = xsMapper.selectXsWithLsh(lsh);
        } else{
            list = xsMapper.selectXsList(null,1);
        }

        return list;
    }


    public List<Th> queryThForTp(String lsh) {
        List<Th> list;

        if(StringUtils.isNotBlank(lsh)){
            list = thMapper.selectThWithLsh(lsh);
        } else{
            Th th = new Th();
            th.setStatus(1);
            list = thMapper.select(th);
        }
        return list;
    }

    public JsonObject appInsertXs(Xs xs) {

        String lsh = commonService.getLsh("XS");
        xs.setLsh(lsh);
        xs.setStatus(0);
        xs.setLrsj(CommonService.getStringDate());
        try {
            xsMapper.insert(xs);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }

        return new JsonData(xs);

    }

    public JsonObject appInsertTh(Th th) {
        String lsh = commonService.getLsh("TH");
        th.setLsh(lsh);
        th.setStatus(0);
        th.setLrsj(CommonService.getStringDate());
        try {
            thMapper.insert(th);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }

        return new JsonData(th);
    }

    public JsonObject appCommitXs(Xs xs) {
        try {
            xsMapper.updateStatusToCommited(xs);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }

        return new JsonData("提交成功");
    }

    public JsonObject appCommitTh(Th th){
        try {
            thMapper.updateStatusToCommited(th);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }
        return new JsonData("提交成功");
    }

    public JsonObject appQueryXs(String lsh) {
        AllBill bill = xsMapper.selectOneBill(lsh);
        return new JsonData(bill);
    }

    public JsonObject appQueryTh(String lsh) {
        AllBill bill = xsMapper.selectOneBill(lsh);
        return new JsonData(bill);
    }

    public JsonObject getProvince() {
        List<Province> provinceList = xsMapper.selectProvince();
        return new JsonData(provinceList);
    }

    public JsonObject getCity(String province) {
        List<City> cityList = xsMapper.selectCity(province);
        return new JsonData(cityList);
    }

    public JsonObject getDistrict(String city) {
        List<District> districtList = xsMapper.selectDistrict(city);
        return new JsonData(districtList);
    }

    public JsonObject queryCityList() {
        JsonData jsonData = new JsonData();
        UserResponseMessage response = new UserResponseMessage();

        List<CityMessage> lastVisitCityList = new ArrayList<>();
        List<CityMessage> hotCityList = new ArrayList<>();
        List<CityMessage> allCityList = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource("city.json");
            File path = resource.getFile();
            String cityStr = FileUtils.readFileToString(path, "UTF-8");
            JSONObject cityObject = JSONObject.fromObject(cityStr);
            JSONArray lastVisitCityArray = null;
            JSONArray hotCityArray = null;
            JSONArray allCityArray = null;
            if (cityObject != null) {
                lastVisitCityArray = cityObject.getJSONArray("lastVisitCityList");
                hotCityArray = cityObject.getJSONArray("hotCityList");
                allCityArray = cityObject.getJSONArray("allCityList");
            }
            lastVisitCityArray.forEach(last -> lastVisitCityList.add(buildCityMessage((JSONObject) last)));
            hotCityArray.forEach(hot -> hotCityList.add(buildCityMessage((JSONObject) hot)));
            allCityArray.forEach(all -> allCityList.add(buildCityMessage((JSONObject) all)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setLastVisitCityList(lastVisitCityList);
        response.setHotCityList(hotCityList);
        response.setAllCityList(allCityList);

        jsonData.setSuccess(true);
        jsonData.setData(response);
        return jsonData;
    }

    private CityMessage buildCityMessage(JSONObject objects) {
        CityMessage cityMessage = new CityMessage();
        cityMessage.setId(objects.getString("id"));
        cityMessage.setName(objects.getString("name"));
        cityMessage.setSpellName(objects.getString("spellName"));
        cityMessage.setFullName(objects.getString("fullName"));
        cityMessage.setSortLetters(objects.getString("sortLetters"));
        return cityMessage;
    }
}
