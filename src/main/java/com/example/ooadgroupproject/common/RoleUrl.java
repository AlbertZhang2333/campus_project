package com.example.ooadgroupproject.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoleUrl {
    private static final String ROLE_ANONYMOUS = "(login//getAccountInfo|/login/logout|/login/loginCheck|/login/register|" +
            "/login/registerVerifyCode|/allBuilding|/searchBuildingName/|" +
            "/searchingBuilding/|/allLine|/searchLineId/|/UserShopping/findAll|/UserShopping/getInstantItems|/Room|" +
            "/TheLoginUserInfo/getLoginUserMail|/Comment/allCommentsUser|/Comment/allCommentsReplyUser|/allStop|/searchStopName/" +
            "|/nearbyStop|/searchingBusStop|/uploadImage/image)";
    private static final String ROLE_USER = "(/login|/UserShopping|/UserCheckShoppingRecord|/Reservation|" +
            "/Comment/addComment|/Comment/updateComment|/ChatController/userSendMessageToAdmin)";
    private static final String ROLE_ADMIN ="(/allRelation|/manageAccount|/ManageItems|/ManageDataAnalysis|/ManageReservationRecord" +
            "|/ManageRoom|/ManageShoppingRecord|/Tool|/addBuilding|/updateBuilding|/deleteBuilding/|/updateLine|/addLine" +
            "|/deleteLine/|/Comment/allReplyCommentsAdmin|/Comment/commentSearchAdmin|" +
            "/Comment/allCommentsAdmin|/ChatController/AdminSendMessageToUser|/addStop|/updateStop|/deleteStop)";
    private static final Pattern pattern_Anonymous = Pattern.compile(ROLE_ANONYMOUS, Pattern.CASE_INSENSITIVE);
    private static final Pattern pattern_User = Pattern.compile(ROLE_USER, Pattern.CASE_INSENSITIVE);
    private static final Pattern pattern_Admin = Pattern.compile(ROLE_ADMIN, Pattern.CASE_INSENSITIVE);
    public static boolean checkAnonymousRole(String url) {
        Matcher matcher = pattern_Anonymous.matcher(url);
        return matcher.find();
    }
    public static boolean checkUserRole(String url) {
        Matcher matcher = pattern_User.matcher(url);
        return matcher.find();
    }
    public static boolean checkAdminRole(String url) {
        Matcher matcher = pattern_Admin.matcher(url);
        return matcher.find();
    }

}
