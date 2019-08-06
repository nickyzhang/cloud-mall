package com.cloud.mapper;

import com.cloud.model.user.UserDetail;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserDetailMapper {

    @Insert("INSERT INTO cloud_user_details(id,user_id,gender,age,birthday," +
            "reg_time,reg_ip,last_login_time,last_login_ip,create_date,update_date)" +
            "VALUES(#{id},#{userId},#{gender},#{age},#{birthday}," +
            "#{registerTime},#{registerIp},#{lastLoginTime},#{lastLoginIp},#{createDate},#{updateDate})")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "gender",property = "gender",jdbcType = JdbcType.VARCHAR),
            @Result(column = "age",property = "age",jdbcType = JdbcType.INTEGER),
            @Result(column = "birthday",property = "birthday",javaType = LocalDate.class,jdbcType=JdbcType.DATE),
            @Result(column = "reg_time",property = "registerTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "reg_ip",property = "registerIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_login_time",property = "lastLoginTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_ip",property = "lastLoginIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP)
    })
    public void save(UserDetail userDetails);

    @Delete({"DELETE FROM cloud_user_details WHERE id = #{id}"})
    public void deleteUserDetails(UserDetail userDetails);

    @Delete({"DELETE FROM cloud_user_details WHERE id = #{id}"})
    public void deleteUserDetailsById(Long id);


    @Update("UPDATE cloud_user_details SET user_id=#{userId},gender=#{gender},age=#{age},"+
            "birthday=#{birthday},reg_time=#{registerTime},reg_ip=#{registerIp},last_login_time=#{lastLoginTime}," +
            "last_login_ip=#{lastLoginIp},create_date=#{createDate},update_date=#{updateDate} WHERE id=#{id}")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "gender",property = "gender",jdbcType = JdbcType.VARCHAR),
            @Result(column = "age",property = "age",jdbcType = JdbcType.INTEGER),
            @Result(column = "birthday",property = "birthday",javaType = LocalDate.class,jdbcType=JdbcType.DATE),
            @Result(column = "reg_time",property = "registerTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "reg_ip",property = "registerIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_login_time",property = "lastLoginTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_ip",property = "lastLoginIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP)
    })
    public void update(UserDetail userDetails);

    @Select("SELECT * FROM cloud_user_details WHERE id=#{id}")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "gender",property = "gender",jdbcType = JdbcType.VARCHAR),
            @Result(column = "age",property = "age",jdbcType = JdbcType.INTEGER),
            @Result(column = "birthday",property = "birthday",javaType = LocalDate.class,jdbcType=JdbcType.DATE),
            @Result(column = "reg_time",property = "registerTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "reg_ip",property = "registerIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_login_time",property = "lastLoginTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_ip",property = "lastLoginIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP)
    })
    public UserDetail findUserDetailsById(Long id);

    @Select("SELECT * FROM cloud_user_details WHERE user_id=#{userId}")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "gender",property = "gender",jdbcType = JdbcType.VARCHAR),
            @Result(column = "age",property = "age",jdbcType = JdbcType.INTEGER),
            @Result(column = "birthday",property = "birthday",javaType = LocalDate.class,jdbcType=JdbcType.DATE),
            @Result(column = "reg_time",property = "registerTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "reg_ip",property = "registerIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_login_time",property = "lastLoginTime",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_ip",property = "lastLoginIp",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP)
    })
    public UserDetail findUserDetailsByUserId(Long userId);
}
