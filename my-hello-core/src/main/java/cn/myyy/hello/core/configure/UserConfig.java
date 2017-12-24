package cn.myyy.hello.core.configure;

import cn.myyy.user.facade.enums.AppTypeEnum;
import cn.myyy.user.facade.enums.DeviceTypeEnum;
import cn.myyy.user.facade.response.OrgVO;
import cn.myyy.user.facade.response.ProjectVO;
import cn.myyy.user.facade.response.RegisterChannelVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: my-hello
 * @date: 2017/12/24
 * @author: SUNYAFEI
 */
public final class UserConfig {

    private static final ProjectVO  p1 = new ProjectVO("P1","OA","后台管理平台");
    private static final List<RegisterChannelVO> registerChannelVOS = new ArrayList<>(5);
    private static final List<ProjectVO> projectVOS = new ArrayList<>(5);
    static {
        projectVOS.add(p1);
        OrgVO orgVO = new OrgVO();
        orgVO.setNo("O1");
        orgVO.setName("机构名称");
        orgVO.setType("TYPE1");

        RegisterChannelVO registerChannelVO = new RegisterChannelVO();
        registerChannelVO.setNo("RegisterChannel_01");
        registerChannelVO.setAppType(AppTypeEnum.H5);
        registerChannelVO.setDeviceType(DeviceTypeEnum.PC);
        registerChannelVO.setName("机构服务平台");
        registerChannelVO.setOrgVO(orgVO);
        registerChannelVO.setProjectVO(p1);

        registerChannelVOS.add(registerChannelVO);
    }

    public static RegisterChannelVO get(String registerChannelNo){
        for(RegisterChannelVO registerChannelVO : registerChannelVOS){
            if(registerChannelVO.getNo().equals(registerChannelNo)){
                return registerChannelVO;
            }
        }
        return null;
    }
}
