package com.cloud.mapper;

import com.cloud.model.user.SocialUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SocialUserMapper {
//
//    @Insert("INSERT INTO cloud_user_auth(id,user_id,open_id,open_type,create_date,update_date)" +
//            "VALUES(#{id},#{userId},#{openId},#{providerId},#{createDate},#{updateDate})")
//    @Results({
//            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
//            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
//            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
//    })
//    public int save(SocialUser user);
//
//    @Insert({
//            "<script>",
//            "INSERT INTO cloud_user_auth(id,user_id,open_id,open_type,create_date,update_date)",
//            "VALUES",
//            "<foreach collection='userList' item='element' index='index' separator=','>",
//            "(#{element.id},#{element.userId},#{element.openId},#{element.providerId},#{element.createDate},#{element.updateDate})",
//            "</foreach>",
//            "</script>"
//    })
//    @Results({
//            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
//            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
//            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
//    })
//    public int saveList(@Param(value = "userList") List<SocialUser> userList);
//
//    @Delete({"DELETE FROM cloud_user_auth WHERE id = #{id}"})
//    public int deleteSocialUserById(Long id);
//
//    @Delete({"DELETE FROM cloud_user_auth WHERE user_id = #{userId}"})
//    public int deleteSocialUserByUserId(Long userId);
//
//    @Update("UPDATE cloud_user_auth SET user_id=#{userId},open_id=#{openId},open_type=#{providerId}, " +
//            "create_date=#{createDate},update_date=#{updateDate} WHERE id=#{id}")
//    @Results({
//            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
//            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
//            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
//    })
//    public int update(SocialUser user);
//
//    @Select("SELECT * FROM cloud_user_auth WHERE id=#{id}")
//    @Results({
//            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
//            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
//            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
//    })
//    public SocialUser findSocialUserById(Long id);
//
//    @Select("SELECT * FROM cloud_user_auth WHERE open_id=#{openId}")
//    @Results({
//            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
//            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
//            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
//    })
//    public SocialUser findSocialUserByOpenId(String openId);
//
//
//    @Select("SELECT * FROM cloud_user_auth WHERE user_id=#{userId}")
//    @Results({
//            @Result(column = "id",property = "id",jdbcType = JdbcType.BIGINT, id = true),
//            @Result(column = "user_id",property = "userId",jdbcType = JdbcType.BIGINT),
//            @Result(column = "open_id",property = "openId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "open_type",property = "providerId",jdbcType = JdbcType.VARCHAR),
//            @Result(column = "create_date",property = "createDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP),
//            @Result(column = "update_date",property = "updateDate",javaType=LocalDateTime.class,jdbcType=JdbcType.TIMESTAMP)
//    })
//    public SocialUser findSocialUserByUserId(String userId);


    public int save(SocialUser user);

    public int saveList(List<SocialUser> userList);

    public int deleteSocialUserById(Long id);

    public int deleteSocialUserByUserId(Long userId);

    public int update(SocialUser user);

    public SocialUser findSocialUserById(Long id);

    public SocialUser findSocialUserByOpenId(String openId);

    public SocialUser findSocialUserByUserId(String userId);
}
