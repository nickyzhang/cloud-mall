package com.cloud.mapper;

import com.cloud.mapper.provider.UserSqlProvider;
import com.cloud.model.user.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * User Mapper
 *
 * @author nickyzhang
 * @since 0.0.1
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO cloud_user(user_id,username,password,salt,phone,email,nickname,avator,status,create_date," +
            "update_date) VALUES(#{userId},#{username},#{password},#{salt},#{phone},#{email},#{nickname},#{avator},#{status}," +
            "#{createDate},#{updateDate})")
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public int save(User user);

    @Insert({
            "<script>",
            "INSERT INTO cloud_user (user_id,username,password,salt,phone,email,nickname,avator,status,create_date,update_date)",
            "VALUES",
            "<foreach collection='userList' item='element' index='index' separator=','>",
            "(#{element.userId},#{element.username},#{element.password},#{element.salt},#{element.phone},#{element.email},#{element.nickname },#{element.avator},#{element.status},#{element.createDate},#{element.updateDate})",
            "</foreach>",
            "</script>"
    })
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public int saveList(@Param(value = "userList") List<User> userList);

    @Delete({"DELETE FROM cloud_user WHERE user_id = #{userId}"})
    public int deleteUser(User user);

    @Delete({"DELETE FROM cloud_user WHERE user_id = #{userId}"})
    public int deleteByUserId(Long userId);

    @Update("UPDATE cloud_user SET username=#{username},password=#{password},salt=#{salt},phone=#{phone}," +
            "email=#{email},nickname=#{nickname},avator=#{avator},status=#{status},create_date=#{createDate}," +
            "update_date=#{updateDate} WHERE user_id=#{userId}")
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public int update(User user);

    @Select("SELECT * FROM cloud_user WHERE user_id=#{userId}")
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public User findByUserId(Long userId);

    @SelectProvider(type = UserSqlProvider.class, method = "findByCondition")
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public List<User> findByCondition(Map<String, Object> conditions);

    @Select("SELECT user_id FROM cloud_user")
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public List<Long> findAll();

    @Select("SELECT user_id FROM cloud_user WHERE user_id > #{start} AND user_id < #{end}")
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public List<Long> findUserListByRange(@Param("start") Long start, @Param("end") Long end);

    @Select("SELECT user_id FROM cloud_user LIMIT #{start},#{offset}")
    @Results({
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "username",property = "username",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR),
            @Result(column = "salt",property = "salt",jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone",property = "phone",jdbcType = JdbcType.VARCHAR),
            @Result(column = "email",property = "email",jdbcType = JdbcType.VARCHAR),
            @Result(column = "nickname",property = "nickname",jdbcType = JdbcType.VARCHAR),
            @Result(column = "avator",property = "avator",jdbcType = JdbcType.VARCHAR),
            @Result(column = "status",property = "status",jdbcType = JdbcType.TINYINT),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public List<Long> findUserListByLimit(@Param("start") Long start, @Param("offset") Long offset);
}
