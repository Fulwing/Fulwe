package com.fulwin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Cusinfo;

import java.util.List;

public interface CusinfoService extends IService<Cusinfo> {

    public List<Cusinfo> getAllCusinfo();

    public Cusinfo getCusinfoById(Long id);

    public void insertCusInfo(Cusinfo cusinfo);

    public void updateCusInfo(Cusinfo cusinfo);

    public void deleteCusInfo(Long id);
}
