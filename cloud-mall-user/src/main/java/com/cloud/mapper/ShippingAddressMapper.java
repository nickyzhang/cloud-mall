package com.cloud.mapper;

import com.cloud.model.user.ShippingAddress;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ShippingAddressMapper {

    @Insert("INSERT INTO cloud_user_shipping_address(address_id,user_id,country,province,city,area,details,create_date,update_date)"+
            "VALUES(#{addressId},#{userId},#{country},#{province},#{city}," +
            "#{area},#{details},#{createDate},#{updateDate})")
    @Results({
            @Result(column = "address_id",property = "addressId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "country",property = "country",jdbcType = JdbcType.VARCHAR),
            @Result(column = "province",property = "province",jdbcType = JdbcType.VARCHAR),
            @Result(column = "city",property = "city",jdbcType = JdbcType.VARCHAR),
            @Result(column = "area",property = "area",jdbcType = JdbcType.VARCHAR),
            @Result(column = "details",property = "details",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public int save(ShippingAddress address);

    @Insert({
        "<script>",
        "INSERT INTO cloud_user_shipping_address (address_id,user_id,country,province,city,area,details,create_date,update_date)",
        "VALUES",
        "<foreach collection='addressList' item='element' index='index' separator=','>",
        "(#{element.addressId},#{element.userId},#{element.country},#{element.province},#{element.city},#{element.area},#{element.details},#{element.createDate},#{element.updateDate})",
        "</foreach>",
        "</script>"
    })
    @Results({
            @Result(column = "address_id",property = "addressId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "country",property = "country",jdbcType = JdbcType.VARCHAR),
            @Result(column = "province",property = "province",jdbcType = JdbcType.VARCHAR),
            @Result(column = "city",property = "city",jdbcType = JdbcType.VARCHAR),
            @Result(column = "area",property = "area",jdbcType = JdbcType.VARCHAR),
            @Result(column = "details",property = "details",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public int saveList(@Param(value = "addressList") List<ShippingAddress> addressList);

    @Delete("DELETE FROM cloud_user_shipping_address WHERE address_id=#{addressId}")
    public int deleteShippingAddressByAddressId(Long addressId);

    @Delete("DELETE FROM cloud_user_shipping_address WHERE user_id=#{userId}")
    public int deleteShippingAddressByUserId(Long userId);

    @Update("UPDATE cloud_user_shipping_address SET user_id=#{userId},country=#{country},province=#{province},city=#{city}," +
            "area=#{area},details=#{details},create_date=#{createDate},update_date=#{updateDate} WHERE address_id=#{addressId}")
    @Results({
            @Result(column = "address_id",property = "addressId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "country",property = "country",jdbcType = JdbcType.VARCHAR),
            @Result(column = "province",property = "province",jdbcType = JdbcType.VARCHAR),
            @Result(column = "city",property = "city",jdbcType = JdbcType.VARCHAR),
            @Result(column = "area",property = "area",jdbcType = JdbcType.VARCHAR),
            @Result(column = "details",property = "details",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public int update(ShippingAddress address);

    @Select("SELECT * FROM cloud_user_shipping_address WHERE address_id=#{addressId}")
    @Results({
            @Result(column = "address_id",property = "addressId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "country",property = "country",jdbcType = JdbcType.VARCHAR),
            @Result(column = "province",property = "province",jdbcType = JdbcType.VARCHAR),
            @Result(column = "city",property = "city",jdbcType = JdbcType.VARCHAR),
            @Result(column = "area",property = "area",jdbcType = JdbcType.VARCHAR),
            @Result(column = "details",property = "details",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public ShippingAddress findShippingAddressByAddressId(Long addressId);

    @Select("SELECT * FROM cloud_user_shipping_address WHERE user_id=#{userId}")
    @Results({
            @Result(column = "address_id",property = "addressId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "country",property = "country",jdbcType = JdbcType.VARCHAR),
            @Result(column = "province",property = "province",jdbcType = JdbcType.VARCHAR),
            @Result(column = "city",property = "city",jdbcType = JdbcType.VARCHAR),
            @Result(column = "area",property = "area",jdbcType = JdbcType.VARCHAR),
            @Result(column = "details",property = "details",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public List<ShippingAddress> findShippingAddressListByUserId(Long userId);
}
