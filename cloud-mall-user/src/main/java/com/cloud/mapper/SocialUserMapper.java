package com.cloud.mapper;

import com.cloud.model.user.SocialUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.time.LocalDateTime;

@Mapper
public interface SocialUserMapper {

    @Insert("INSERT INTO cloud_user_auth(id,user_id,open_id,open_type,create_date,update_date)" +
            "VALUES(#{id},#{userId},#{openId},#{providerId},#{createDate},#{updateDate})")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public void save(SocialUser user);

    @Delete({"DELETE FROM cloud_user_auth WHERE id = #{id}"})
    public void deleteSocialUser(SocialUser user);

    @Delete({"DELETE FROM cloud_user_auth WHERE id = #{id}"})
    public void deleteSocialUserById(Long id);

    @Delete({"DELETE FROM cloud_user_auth WHERE user_id = #{userId}"})
    public void deleteSocialUserByUserId(Long userId);

    @Update("UPDATE cloud_user_auth SET user_id=#{userId},open_id=#{openId},open_type=#{providerId}, " +
            "create_date=#{createDate},update_date=#{updateDate} WHERE id=#{id}")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public void update(SocialUser user);

    @Select("SELECT * FROM cloud_user_auth WHERE id=#{id}")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public SocialUser findSocialUserById(Long id);

    @Select("SELECT * FROM cloud_user_auth WHERE open_id=#{openId}")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public SocialUser findSocialUserByOpenId(String openId);


    @Select("SELECT * FROM cloud_user_auth WHERE user_id=#{userId}")
    @Results({
            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
    })
    public SocialUser findSocialUserByUserId(String userId);
}
