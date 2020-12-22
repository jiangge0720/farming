package com.crowdfunding.farming.service.impl;

import com.crowdfunding.farming.mapper.AddressMapper;
import com.crowdfunding.farming.mapper.RegionMapper;
import com.crowdfunding.farming.pojo.Address;
import com.crowdfunding.farming.pojo.Region;
import com.crowdfunding.farming.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiang-gege
 * 2020/10/1418:03
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Address> queryMyAddress(Integer userId) {
        Address record = new Address();
        record.setUserId(userId);
        List<Address> myAddress = this.addressMapper.select(record);
        return myAddress;
    }

    @Override
    public Integer createrAddress(Address address) {
        address.setAddressId(null);
        Integer result = addressMapper.insertSelective(address);
        return result;
    }

    @Override
    public Boolean deleteAddress(Integer addressId) {
        Address record = new Address();
        record.setAddressId(addressId);
        int count  = this.addressMapper.delete(record);
        return count == 1;
    }

    @Override
    public List<Region> queryRegionByParentCode(String parentCode) {
        Region record = new Region();
        record.setParentCode(parentCode);
        List<Region> regions = regionMapper.select(record);
        return regions;
    }
}