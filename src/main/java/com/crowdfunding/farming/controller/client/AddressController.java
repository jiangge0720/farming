package com.crowdfunding.farming.controller.client;

import com.crowdfunding.farming.pojo.Address;
import com.crowdfunding.farming.pojo.Region;
import com.crowdfunding.farming.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiang-gege
 * 2020/10/1418:04
 */
@RestController
@RequestMapping("address")
@Api("地址服务接口")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping
    @ApiOperation(value = "查询地址接口，返回用户所有地址", notes = "查询用户所有地址")
    public ResponseEntity<List<Address>> queryMyAddress(@RequestParam("userId") Integer userId){
        List<Address> myAddress = addressService.queryMyAddress(userId);
        return ResponseEntity.ok(myAddress);
    }

    @PostMapping
    @ApiOperation(value = "新增地址", notes = "新增地址")
    public ResponseEntity<Integer> createAddress(@RequestBody Address address){
        Integer result = addressService.createrAddress(address);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除地址", notes = "删除地址")
    public ResponseEntity<Boolean> delete(@RequestParam("addressId") Integer addressId){
        Boolean result = addressService.deleteAddress(addressId);
        return ResponseEntity.ok(result);
    }

    //根据父id查询地址,顶级父类为“000000”
    @GetMapping("/id")
    @ApiOperation(value = "根据父id查询地区接口，返回地区" ,notes = "根据父id查询地区接口")
    public ResponseEntity<List<Region>> queryRegionByParentCode(
            @RequestParam(value = "parentCode",defaultValue = "000000") String parentCode){
        return ResponseEntity.ok(addressService.queryRegionByParentCode(parentCode));
    }
}