package com.zzx.service;

import com.zzx.beans.Type;
import com.zzx.dao.TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TypeService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/19 21:56
 **/
@Service
public class TypeService {
    @Autowired
    TypeDao typeDao;

    public List<Type> getTypes(){
        return typeDao.selectAll();
    }

    public List<Type> getTypeSum() {
        return typeDao.findTypesSum();
    }

    public String getTypeByid(int typeId) {
        return typeDao.getTypeById(typeId);
    }
}
