package com.fulwin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fulwin.mapper.CusinfoMapper;
import com.fulwin.pojo.Cusinfo;
import com.fulwin.service.CusinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CusinfoServiceImpl extends ServiceImpl<CusinfoMapper, Cusinfo> implements CusinfoService {

    @Autowired
    private CusinfoMapper cusinfoMapper;

    @Override
    public List<Cusinfo> getAllCusinfo() {
        return cusinfoMapper.selectList(null);
    }

    @Override
    public Cusinfo getCusinfoById(Long id) {
        return cusinfoMapper.selectById(id);
    }

    @Override
    public void insertCusInfo(Cusinfo cusinfo) {
        cusinfoMapper.insert(cusinfo);
    }

    @Override
    public void updateCusInfo(Cusinfo cusinfo) {
        cusinfoMapper.updateById(cusinfo);
    }

    @Override
    public void deleteCusInfo(Long id) {
        cusinfoMapper.deleteById(id);
    }
}
