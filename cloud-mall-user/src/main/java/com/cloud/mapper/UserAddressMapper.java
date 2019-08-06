package com.cloud.mapper;

import com.cloud.model.user.UserAddress;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.time.LocalDateTime;

public interface UserAddressMapper {

    @Insert("INSERT INTO cloud_user_address(address_id,user_id,country,province,city,area,details,create_date,update_date)"+
            "VALUES(address_id=#{addressId},user_id=#{userId},country=#{country},province=#{province},city=#{city}," +
            "area=#{area},details=#{details},create_date=#{createDate},update_date=#{updateDate})")
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
    public void save(UserAddress address);

    @Delete("DELETE FROM cloud_user_address WHERE address_id=#{addressId}")
    public void deleteUserAddress(Long address);

    @Delete("DELETE FROM cloud_user_address WHERE address_id=#{addressId}")
    public void deleteUserAddressByAddressId(Long addressId);

    @Delete("DELETE FROM cloud_user_address WHERE user_id=#{userId}")
    public void deleteUserAddressByUserId(Long userId);

    @Update("UPDATE cloud_user_address SET user_id=#{userId},country=#{country},province=#{province},city=#{city}," +
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
    public void updateUserAddress(UserAddress address);

    @Select("SELECT * FROM cloud_user_address WHERE address_id=#{addressId}")
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
    public void findUserAddressByAddressId(Long addressId);

    @Select("SELECT * FROM cloud_user_address WHERE user_id=#{userId}")
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
    public void findUserAddressByUserId(Long userId);
}
