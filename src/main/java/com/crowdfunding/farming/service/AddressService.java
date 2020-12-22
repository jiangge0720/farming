package com.crowdfunding.farming.service;

import com.crowdfunding.farming.pojo.Address;
import com.crowdfunding.farming.pojo.Region;

import java.util.List;

/**
 * @author Jiang-gege
 * 2020/10/1418:02
 */
public interface AddressService {
    List<Address> queryMyAddress(Integer userId);

    Integer createrAddress(Address address);

    Boolean deleteAddress(Integer address);

    List<Region>  queryRegionByParentCode(String parentCode);
}
