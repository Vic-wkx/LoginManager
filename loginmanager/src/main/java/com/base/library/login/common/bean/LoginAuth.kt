package com.base.library.login.common.bean

/**
 * Description:
 * 登录完成后的bean
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/6
 */
class LoginAuth(
    // token，实际产品中，只需要获取token，将token传到后台，让后台获取个人信息并保存。
    // 因为这样处理有利于用户修改头像和昵称
    var token: String = "",
    // 名字
    var name: String = "",
    // 头像url
    var avatar: String = "",
    // 邮箱
    var email: String = "",
    // twitter获取个人信息需要的参数，如果使用推特登录，需要将twitterSecret也传到后台
    var twitterSecret: String = ""
){
    override fun toString(): String {
        return "name:$name\n\navatar:$avatar\n\nemail:$email\n\ntoken:$token\n\ntwitterSecret:$twitterSecret\n\n"
    }
}