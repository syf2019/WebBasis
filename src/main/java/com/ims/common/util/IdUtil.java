package com.ims.common.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * 类名:com.tunan.utils.IdUtils
 * 描述:ID生成工具类
 * 编写者:陈骑元
 * 创建时间:2016年7月18日 下午1:46:27
 * 修改说明:
 */
public class IdUtil {
	/**
	 * 返回去除连接符-的UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		String uuid = uuid2();
		return uuid.replaceAll("-", "");
	}
	
	/**
	 * 返回原生UUID
	 * 
	 * @return
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 返回原始UUID中指定序号的一组字符串。
	 * 
	 * @param index
	 *            有效索引序号[0,1,2,3,4]。
	 * @return
	 */
	public static String uuid(int index) {
		String[] uuids = uuid2().split("-");
		return uuids[index];
	}
	

   /**
    * 
    * 简要说明：随机生成16位UUID
    * 编写者：陈骑元
    * 创建时间：2019年1月16日 上午10:53:43
    * @param 说明
    * @return 说明
    */
	public static String uuid16() {
        int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        
        return first + String.format("%015d", hashCodeV);
    }




	/**
	 * 生成树路径ID，如：01.01.01
	 * 
	 * @param curMaxNode
	 *            本级当前最大节点ID，如果要生成本级第一个节点则传XX.XX.00(XX.XX为父节点ID)。
	 * @param maxValue
	 *            本级节点ID允许的最大值
	 * @return
	 */
	public static String treeId(String curMaxNode, int maxValue) {
		String prefix = StringUtils.substringBeforeLast(curMaxNode, ".");
		String last = StringUtils.substringAfterLast(curMaxNode, ".");
		if (IMSUtil.isEmpty(last)) {
			throw new RuntimeException("树ID生成器生成节点ID参数错误,节点必须符合X.X的格式，比如创建第一级节点0.XX(0表示根节点，根节点也可以用其它值表示)节点必须符合X.X的格式，比如创建第一级节点0.XX(0表示根节点，根节点也可以用其它值表示)");
		}
		int intLast = Integer.valueOf(last);
		if (intLast == maxValue || intLast > maxValue) {
			throw new RuntimeException("树ID生成器本级节点号源用,请检查相关参数");
		}
		String thisNode = String.valueOf(intLast + 1);
		thisNode = StringUtils.leftPad(thisNode, String.valueOf(maxValue).length(), "0");
		return prefix + "." + thisNode;
	}
   
}
