package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.util.DES;
import com.example.util.Image;


public class text {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Date a1 = new Date();
		map.put("uid", "order");
		map.put("pw", "o1Fu@1X");
		map.put("date", a1.getTime());

		String str = JSON.toJSON(map).toString();
		map.clear();
		map.put("bussid", "A001");

		byte[] encrybyte = DES.encryptMode(str.getBytes());
		String encryStr = DES.bytesToHexString(encrybyte);

		String key = sendPost("http://127.0.0.1:8080/system/servlet/userAuthor", map, encryStr);
		System.out.println(key);

		String base = Image.GetImageStr("D:\\8.png");
		byte[] encrybyte1 = DES.encryptMode(base.getBytes());
		String encryStr1 = DES.bytesToHexString(encrybyte1);
		map.clear();
		map.put("imgBase", encryStr1);
//		String o = sendPost("http://127.0.0.1:8080//order/servlet/image", map, key);
//		 System.out.println(o);
//		 E5704063DF995A4BEFB3A4971E396695

		String img_id = "58658";
		
		
		map.clear();
		map.put("codeName", "拍照要求");
		
		
		String codeName = sendPost("http://127.0.0.1:8080/system/servlet/codes", map, encryStr);
		System.out.println(codeName);
		JSONObject json = new JSONObject();
	
		json.put("jsordertype", "js订单类型");
		json.put("custName", "福州杰生旗帜工艺品有限公司");
		json.put("stockNo", "160520NC0223-NorthStar0523");
		json.put("signDate", new Date());
		json.put("deliverDate", new Date());
		json.put("remark", "");
		json.put("custOpName", "");
		json.put("customer", "客户名称");
		json.put("customerNo", "po号");
		JSONArray products = new JSONArray();
		JSONObject product = new JSONObject();
		
		product.put("serialNumber", "其它.产品序列号");
		product.put("productRemark", "其它.备注");
		product.put("stockID", 1);
		product.put("imgId", img_id);
		product.put("firstConfirmed", "首样确认");
		product.put("layerNumber", "其它.层数");
		product.put("sprayPrintingRemark", "其它.喷印备注");
		product.put("sewingWaistHead", "其它.缝纫方式");
		product.put("goodscode", "产品编码");
		product.put("goodscDesc", "中文描述");
		product.put("goodseDesc", "英文描述");
		product.put("goodscname", "中文名称");
		product.put("goodsename", "英文名称");
		product.put("otherProductType", "其它.产品细类");
		product.put("otherFlagsSize", "其它.旗帜尺寸");
		product.put("OtherBannerCloth", "其它.旗帜布料");
		product.put("OtherPantoneColorNo", "其它.潘通色号");
		product.put("OtherWaistSizeCloth", "其它.腰头尺寸布料");
		product.put("OtherAccessories", "其它.配件");
		product.put("OtherHem", "其它.缝边");
		product.put("OtherLabel", "其它.标签");
		product.put("OtherPackaging", "其它.包装");
		product.put("OtherPrinting", "其它.印刷");
		product.put("OtherPhoto", "其它.拍照要求");
		product.put("GoodsNum", 1);
		product.put("OtherPurposeNumber", "其它.留底数量");
		product.put("SandwichName", "其它.帐篷画面");
		product.put("unitprice", "");
		product.put("totalprice", "");
//		product.put("CuttingRemark", "其它.裁剪备注");
//		product.put("SewingRemark", "其它.缝纫备注");
//		product.put("PackagingRemark", "其它.包装备注");
//		product.put("SprayPrintingRemark", "其它.喷印备注");
//		product.put("Remark", "");
//		product.put("TotalpricePI","其它.PI总价");
//		product.put("UnitpricePI", "其它.PI单价");
//		product.put("ModelNumber", "其它.款数");
		JSONArray productSizes = new JSONArray();
		JSONObject productSize = new JSONObject();
		productSize.put("layerNumber", 1);
		productSize.put("amount", 1);
		productSize.put("modelNumber", 1);
		productSize.put("sprayPrintWide", 1.01);
		productSize.put("sprayPrintLong", 1.01);
		productSize.put("psName", "A");
		productSizes.add(productSize);
		product.put("productSizes", productSizes);
		products.add(product);
		
		json.put("products", products);
		
		
		System.out.println(json.toString());
		map.clear();
		map.put("jsonStr", json.toString());
		String o = sendPost("http://127.0.0.1:8080/system/servlet/orders", map, key);
		System.out.println(o);
	}

	public static String error(String err) {
		String str = "";
		switch (err) {
		case "00":
			str = "接口调用成功";
			break;
		case "10":
			str = "未找到匹配的信息";
			break;
		case "11":
			str = "要求必填的输入参数值为空";
			break;
		case "12":
			str = "身份验证不通过或GUID过期";
			break;
		case "13":
			str = "用户无权限操作该接口";
			break;
		case "14":
			str = "电子证照或批文制作单位找不到（单位需用全称）或证照目录登记单位找不到（单位需用全称）";
			break;
		case "15":
			str = "XML文件的格式不对";
			break;
		case "16":
			str = "XML文件节点不全";
			break;
		case "17":
			str = "XML文件中必填节点值为空";
			break;
		case "18":
			str = "电子证照类型未注册或批文的文号类型未注册";
			break;
		case "19":
			str = "电文文件损坏（MD5不匹配）";
			break;
		case "20":
			str = "该电子证照已超出有效期";
			break;
		case "21":
			str = "该电子证照已废止";
			break;
		case "22":
			str = "照面信息结构不符";
			break;
		case "23":
			str = "电文文件未包含照面信息";
			break;
		case "24":
			str = "该电子证照已受限使用";
			break;
		case "25":
			str = "文件类型找不到(请使用：废止、受限使用)";
			break;
		case "26":
			str = "文件扩展名不正确";
			break;
		case "27":
			str = "电子证照颁证单位或电子批文颁发单位找不到（单位需用全称）";
			break;

		default:
			break;
		}
		return str;
	}

	/**
	 * 向指定URL发送POST请求
	 * 
	 * @param url
	 * @param paramMap
	 * @return 响应结果
	 */
	public static String sendPost(String url, Map<String, Object> paramMap, String kye) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("token", kye);
			conn.setRequestProperty("key", kye);

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());

			// 设置请求属性
			String param = "";
			if (paramMap != null && paramMap.size() > 0) {
				Iterator<String> ite = paramMap.keySet().iterator();
				while (ite.hasNext()) {
					String key = ite.next();// key
					String value = paramMap.get(key).toString();
					param += key + "=" + value + "&";
				}
				param = param.substring(0, param.length() - 1);
			}

			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.err.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}

//allowpasting
//{
//  'auditUserid': 0,
//  'coId': 44661,
//  'createtime': 1463715612000,
//  'custName': '福州杰生旗帜工艺品有限公司',
//  'custOpName': '童丽丽',
//  'customer': 'NorthStar Flag & Flagpole Co.',
//  'customerNo': 'PO402000',
//  'customerRank': '客人等级',
//  'deliverDate': 1463932800000,
//  'id': 29,
//  'operationtime': 1507034335000,
//  'operationuid': 1,
//  'orderDate': 1463715590000,
//  'orderUserName': '陈梅',
//  'orderUserid': 11,
//  'productImage': false,
//  'productionSingle': false,
//  'productionSingleDate': 1463731544000,
//  'products': [
//    {
//      'createtime': 1463715597000,
//      'cuttingRemark': '',
//      'cuttingWay': '单层',
//      'firstConfirmed': '0',
//      'goodscdesc': '产品尺寸88"×130"（223.52*330.2cm）\r\n产品布料：(170g防皱）300D 涤纶\r\n产品logo：潘通色卡号如图稿\r\n腰头及尺寸布料：无\r\n产品配件：无\r\n印刷工艺：热转(拼接必须要在桌布的后面)\r\n产品缝边：双线缝边+注意拼接位置要富通图片的拼接方法拼接+桌布做出来之后，拼接线要沿着桌面的边缘，不能偏下，见富通图片\r\n产品标签：缝NorthStar旗子水洗标，水洗标内容：NorthStarflag，见富通。水洗标要缝在拼接线的里面\r\n产品包装：1面/厚PVC（30*30*5cm黑色拉链)袋，拉链袋末端要缝死，确保拉链的扣子不会跑出来（客人有投诉过，拉链的扣子会跑出来，见图片）+印有logo的纸质标签\r\n拍照要求：常规出货图',
//      'goodscname': '产品名称',
//      'goodscode': '桌布',
//      'goodsedesc': '产品尺寸88"×130"（223.52*330.2cm）\r\n产品布料：(170g防皱）300D 涤纶\r\n产品logo：潘通色卡号如图稿\r\n腰头及尺寸布料：无\r\n产品配件：无\r\n印刷工艺：热转(拼接必须要在桌布的后面)\r\n产品缝边：双线缝边+注意拼接位置要富通图片的拼接方法拼接+桌布做出来之后，拼接线要沿着桌面的边缘，不能偏下，见富通图片\r\n产品标签：缝NorthStar旗子水洗标，水洗标内容：NorthStarflag，见富通。水洗标要缝在拼接线的里面\r\n产品包装：1面/厚PVC（30*30*5cm黑色拉链)袋，拉链袋末端要缝死，确保拉链的扣子不会跑出来（客人有投诉过，拉链的扣子会跑出来，见图片）+印有logo的纸质标签\r\n拍照要求：常规出货图',
//      'goodsename': 'SA Table Cover',
//      'goodsnum': 1,
//      'id': 35,
//      'imageguid': '20160519E3047F7600E644639B7986668648CC58',
//      'imgName': '/uploadFile/order/chenmei/2016/05/160520NC0223-NorthStar0523/201605201539587079307.jpg',
//      'metricWide': 88,
//      'metriclong': 130,
//      'operationtime': 1463715597000,
//      'operationuid': 11,
//      'otherAccessories': '其它配件',
//      'otherBannerCloth': '配件订单',
//      'otherFlagsSize': '尺寸备注',
//      'otherHem': ';双线缝边+注意拼接位置要富通图片的拼接方法拼接+桌布做出来之后，拼接线要沿着桌面的边缘，不能偏下，见富通图片',
//      'otherLabel': '缝NorthStar旗子水洗标，水洗标内容：NorthStarflag，见富通。水',
//      'otherPackaging': '1面/厚PVC（30*30*5cm黑色拉链)袋，拉链袋末端要缝死，确保拉链的扣子不会跑出来（客人有投诉过，拉链的扣子会跑出来，见图片）+印有logo的纸质标签',
//      'otherPantoneColorNo': '',
//      'otherPhoto': '常规出货图',
//      'otherPrinting': '热转印',
//      'otherProductType': '桌布',
//      'otherPurposeNumber': '0',
//      'otherWaistSizeCloth': '腰头尺寸',
//      'packagingRemark': '',
//      'productLong': 330.2,
//      'productRemark': '产品备注',
//      'productSizes': [
//        {
//          'amount': 1,
//          'createtime': 1507035814000,
//          'id': 47131,
//          'layerNumber': 1,
//          'metricLong': 0,
//          'metricWide': 0,
//          'modelNumber': 1,
//          'operationtime': 1507035814000,
//          'operationuid': 1,
//          'productLong': 0,
//          'productWide': 0,
//          'propertyid': 0,
//          'psName': 'B',
//          'seq': 0,
//          'sprayPrintLong': 1,
//          'sprayPrintWide': 1,
//          'state': false,
//          'stockNo': '160520NC0223-NorthStar0523'
//        },
//        {
//          'amount': 1,
//          'createtime': 1463729993000,
//          'id': 75,
//          'layerNumber': 1,
//          'metricLong': 0,
//          'metricWide': 0,
//          'modelNumber': 1,
//          'operationtime': 1507035814000,
//          'operationuid': 1,
//          'product': {
//            '$ref': '$.products[0]'
//          },
//          'productLong': 0,
//          'productWide': 0,
//          'propertyid': 0,
//          'psName': 'A',
//          'seq': 0,
//          'sprayPrintLong': 334.2,
//          'sprayPrintWide': 227.52,
//          'state': false,
//          'stockNo': '160520NC0223-NorthStar0523'
//        }
//      ],
//      'productWide': 223.52,
//      'quantity': 0,
//      'quantitydifference': 1,
//      'recguid': '2016052094BA54C365E94295906A9C44E0C29C41',
//      'recordDetail': [
//      ],
//      'sandwichName': '帐篷画面',
//      'seq': 0,
//      'serialNumber': '0.0',
//      'sewingRemark': '',
//      'shipement': false,
//      'sprayPrintLong': 0,
//      'sprayPrintWide': 0,
//      'sprayPrintingRemark': '',
//      'state': false,
//      'stockID': 1,
//      'stockNo': '160520NC0223-NorthStar0523',
//      'stockProductId': 116167,
//      'thumblmageName': '/uploadFile/order/chenmei/2016/05/160520NC0223-NorthStar0523/201605201539587079307.jpg',
//      'totalprice': 0,
//      'totalpricePI': 0,
//      'unit': '英寸',
//      'unitprice': 0,
//      'unitpricePI': 0
//    },
//    {
//      'contract': {
//        '$ref': '$'
//      },
//      'createtime': 1463715597000,
//      'cuttingRemark': '',
//      'cuttingWay': '',
//      'firstConfirmed': '0',
//      'fittinglists': [
//      ],
//      'goodscdesc': '产品货号：PT-C-25\r\n产品名称：皮筋绳',
//      'goodscname': '皮筋绳',
//      'goodscode': 'PT-C-25',
//      'goodsedesc': '产品货号：PT-C-25\r\n产品名称：皮筋绳',
//      'goodsename': '50cm Long Elastic Rope',
//      'goodsnum': 3,
//      'id': 37,
//      'imgName': '/uploadFile/order/chenmei/2016/05/160520NC0224-Adverflag0523/201605201544189007292.jpg',
//      'metricWide': 0,
//      'metriclong': 0,
//      'operationtime': 1463715597000,
//      'operationuid': 11,
//      'otherAccessories': 'PT-C-25',
//      'otherBannerCloth': '配件订单',
//      'otherFlagsSize': '',
//      'otherHem': ';',
//      'otherLabel': '',
//      'otherPackaging': '',
//      'otherPantoneColorNo': '',
//      'otherPhoto': '',
//      'otherPrinting': '配件订单',
//      'otherProductType': '',
//      'otherPurposeNumber': '0',
//      'otherWaistSizeCloth': '',
//      'packagingRemark': '',
//      'productLong': 0,
//      'productRemark': '',
//      'productSizes': [
//      ],
//      'productWide': 0,
//      'quantity': 0,
//      'quantitydifference': 3,
//      'recguid': '20160520D45CFBA3E58C4598ADADAD378B9FB10D',
//      'recordDetail': [
//      ],
//      'sandwichName': '',
//      'seq': 0,
//      'serialNumber': '0.0',
//      'sewingRemark': '',
//      'shipement': false,
//      'sprayPrintLong': 0,
//      'sprayPrintWide': 0,
//      'sprayPrintingRemark': '',
//      'state': false,
//      'stockID': 2,
//      'stockNo': '160520NC0223-NorthStar0523',
//      'stockProductId': 116174,
//      'thumblmageName': '/uploadFile/order/chenmei/2016/05/160520NC0224-Adverflag0523/201605201544189007292.jpg',
//      'totalprice': 0,
//      'totalpricePI': 0,
//      'unit': '厘米',
//      'unitprice': 0,
//      'unitpricePI': 0
//    }
//  ],
//  'remark': '1）交期不能推！！！\r\n2）业务提交时间：11:00',
//  'saveOrder': false,
//  'seq': 29,
//  'signDate': 1463673600000,
//  'state': false,
//  'stateRemark': '',
//  'status': false,
//  'stockNo': '160520NC0223-NorthStar0523'
//}
//{
//  'auditUserid': 0,
//  'coId': 44661,
//  'createtime': 1463715612000,
//  'custName': '福州杰生旗帜工艺品有限公司',
//  'custOpName': '童丽丽',
//  'customer': 'NorthStar Flag & Flagpole Co.',
//  'customerNo': 'PO402000',
//  'customerRank': '客人等级',
//  'deliverDate': 1463932800000,
//  'id': 29,
//  'operationtime': 1507034335000,
//  'operationuid': 1,
//  'orderDate': 1463715590000,
//  'orderUserName': '陈梅',
//  'orderUserid': 11,
//  'productImage': false,
//  'productionSingle': false,
//  'productionSingleDate': 1463731544000,
//  'products': [
//    {
//      'contract': {
//        '$ref': '$'
//      },
//      'createtime': 1463715597000,
//      'cuttingRemark': '',
//      'cuttingWay': '单层',
//      'firstConfirmed': '0',
//      'fittinglists': [
//      ],
//      'goodscdesc': '产品尺寸88"×130"（223.52*330.2cm）\r\n产品布料：(170g防皱）300D 涤纶\r\n产品logo：潘通色卡号如图稿\r\n腰头及尺寸布料：无\r\n产品配件：无\r\n印刷工艺：热转(拼接必须要在桌布的后面)\r\n产品缝边：双线缝边+注意拼接位置要富通图片的拼接方法拼接+桌布做出来之后，拼接线要沿着桌面的边缘，不能偏下，见富通图片\r\n产品标签：缝NorthStar旗子水洗标，水洗标内容：NorthStarflag，见富通。水洗标要缝在拼接线的里面\r\n产品包装：1面/厚PVC（30*30*5cm黑色拉链)袋，拉链袋末端要缝死，确保拉链的扣子不会跑出来（客人有投诉过，拉链的扣子会跑出来，见图片）+印有logo的纸质标签\r\n拍照要求：常规出货图',
//      'goodscname': '产品名称',
//      'goodscode': '桌布',
//      'goodsedesc': '产品尺寸88"×130"（223.52*330.2cm）\r\n产品布料：(170g防皱）300D 涤纶\r\n产品logo：潘通色卡号如图稿\r\n腰头及尺寸布料：无\r\n产品配件：无\r\n印刷工艺：热转(拼接必须要在桌布的后面)\r\n产品缝边：双线缝边+注意拼接位置要富通图片的拼接方法拼接+桌布做出来之后，拼接���要沿着桌面的边缘，不能偏下，见富通图片\r\n产品标签：缝NorthStar旗子水洗标，水洗标内容：NorthStarflag，见富通。水洗标要缝在拼接线的里面\r\n产品包装：1面/厚PVC（30*30*5cm黑色拉链)袋，拉链袋末端要缝死，确保拉链的扣子不会跑出来（客人有投诉过，拉链的扣子会跑出来，见图片）+印有logo的纸质标签\r\n拍照要求：常规出货图',
//      'goodsename': 'SA Table Cover',
//      'goodsnum': 1,
//      'id': 35,
//      'imageguid': '20160519E3047F7600E644639B7986668648CC58',
//      'imgName': '/uploadFile/order/chenmei/2016/05/160520NC0223-NorthStar0523/201605201539587079307.jpg',
//      'metricWide': 88,
//      'metriclong': 130,
//      'operationtime': 1463715597000,
//      'operationuid': 11,
//      'otherAccessories': '其它配件',
//      'otherBannerCloth': '配件订单',
//      'otherFlagsSize': '尺寸备注',
//      'otherHem': ';双线缝边+注意拼接位置要富通图片的拼接方法拼接+桌布做出来之后，拼接线要沿着桌面的边缘，不能偏下，见富通图片',
//      'otherLabel': '缝NorthStar旗子水洗标，水洗标内容：NorthStarflag，见富通。水',
//      'otherPackaging': '1面/厚PVC（30*30*5cm黑色拉链)袋，拉链袋末端要缝死，确保拉链的扣子不会跑出来（客人有投诉过，拉链的扣子会跑出来，见图片）+印有logo的纸质标签',
//      'otherPantoneColorNo': '',
//      'otherPhoto': '常规出货图',
//      'otherPrinting': '热转印',
//      'otherProductType': '桌布',
//      'otherPurposeNumber': '0',
//      'otherWaistSizeCloth': '腰头尺寸',
//      'packagingRemark': '',
//      'productLong': 330.2,
//      'productRemark': '产品备注',
//      'productSizes': [
//        {
//          'amount': 1,
//          'createtime': 1507035814000,
//          'id': 47131,
//          'layerNumber': 1,
//          'metricLong': 0,
//          'metricWide': 0,
//          'modelNumber': 1,
//          'operationtime': 1507035814000,
//          'operationuid': 1,
//          'product': {
//            '$ref': '$.products[0]'
//          },
//          'productLong': 0,
//          'productWide': 0,
//          'propertyid': 0,
//          'psName': 'B',
//          'seq': 0,
//          'sprayPrintLong': 1,
//          'sprayPrintWide': 1,
//          'state': false,
//          'stockNo': '160520NC0223-NorthStar0523'
//        },
//        {
//          'amount': 1,
//          'createtime': 1463729993000,
//          'id': 75,
//          'layerNumber': 1,
//          'metricLong': 0,
//          'metricWide': 0,
//          'modelNumber': 1,
//          'operationtime': 1507035814000,
//          'operationuid': 1,
//          'product': {
//            '$ref': '$.products[0]'
//          },
//          'productLong': 0,
//          'productWide': 0,
//          'propertyid': 0,
//          'psName': 'A',
//          'seq': 0,
//          'sprayPrintLong': 334.2,
//          'sprayPrintWide': 227.52,
//          'state': false,
//          'stockNo': '160520NC0223-NorthStar0523'
//        }
//      ],
//      'productWide': 223.52,
//      'quantity': 0,
//      'quantitydifference': 1,
//      'recguid': '2016052094BA54C365E94295906A9C44E0C29C41',
//      'recordDetail': [
//      ],
//      'sandwichName': '帐篷画面',
//      'seq': 0,
//      'serialNumber': '0.0',
//      'sewingRemark': '',
//      'shipement': false,
//      'sprayPrintLong': 0,
//      'sprayPrintWide': 0,
//      'sprayPrintingRemark': '',
//      'state': false,
//      'stockID': 1,
//      'stockNo': '160520NC0223-NorthStar0523',
//      'stockProductId': 116167,
//      'thumblmageName': '/uploadFile/order/chenmei/2016/05/160520NC0223-NorthStar0523/201605201539587079307.jpg',
//      'totalprice': 0,
//      'totalpricePI': 0,
//      'unit': '英寸',
//      'unitprice': 0,
//      'unitpricePI': 0
//    },
//    {
//      'contract': {
//        '$ref': '$'
//      },
//      'createtime': 1463715597000,
//      'cuttingRemark': '',
//      'cuttingWay': '',
//      'firstConfirmed': '0',
//      'fittinglists': [
//      ],
//      'goodscdesc': '产品货号：PT-C-25\r\n产品名称：皮筋绳',
//      'goodscname': '皮筋绳',
//      'goodscode': 'PT-C-25',
//      'goodsedesc': '产品货号：PT-C-25\r\n产品名称：皮筋绳',
//      'goodsename': '50cm Long Elastic Rope',
//      'goodsnum': 3,
//      'id': 37,
//      'imgName': '/uploadFile/order/chenmei/2016/05/160520NC0224-Adverflag0523/201605201544189007292.jpg',
//      'metricWide': 0,
//      'metriclong': 0,
//      'operationtime': 1463715597000,
//      'operationuid': 11,
//      'otherAccessories': 'PT-C-25',
//      'otherBannerCloth': '配件订单',
//      'otherFlagsSize': '',
//      'otherHem': ';',
//      'otherLabel': '',
//      'otherPackaging': '',
//      'otherPantoneColorNo': '',
//      'otherPhoto': '',
//      'otherPrinting': '配件订单',
//      'otherProductType': '',
//      'otherPurposeNumber': '0',
//      'otherWaistSizeCloth': '',
//      'packagingRemark': '',
//      'productLong': 0,
//      'productRemark': '',
//      'productSizes': [
//      ],
//      'productWide': 0,
//      'quantity': 0,
//      'quantitydifference': 3,
//      'recguid': '20160520D45CFBA3E58C4598ADADAD378B9FB10D',
//      'recordDetail': [
//      ],
//      'sandwichName': '',
//      'seq': 0,
//      'serialNumber': '0.0',
//      'sewingRemark': '',
//      'shipement': false,
//      'sprayPrintLong': 0,
//      'sprayPrintWide': 0,
//      'sprayPrintingRemark': '',
//      'state': false,
//      'stockID': 2,
//      'stockNo': '160520NC0223-NorthStar0523',
//      'stockProductId': 116174,
//      'thumblmageName': '/uploadFile/order/chenmei/2016/05/160520NC0224-Adverflag0523/201605201544189007292.jpg',
//      'totalprice': 0,
//      'totalpricePI': 0,
//      'unit': '厘米',
//      'unitprice': 0,
//      'unitpricePI': 0
//    }
//  ],
//  'remark': '1）交期不能推！！！\r\n2）业务提交时间：11:00',
//  'saveOrder': false,
//  'seq': 29,
//  'signDate': 1463673600000,
//  'state': false,
//  'stateRemark': '',
//  'status': false,
//  'stockNo': '160520NC0223-NorthStar0523'
//}
//{
//  'custName': '福州杰生旗帜工艺品有限公司',
//  'custOpName': '',
//  'customer': '客户名称',
//  'customerNo': 'po号',
//  'deliverDate': 1507042285244,
//  'jsordertype': 'js订单类型',
//  'products': [
//    {
//      'CuttingRemark': '其它.裁剪备注',
//      'GoodsNum': 1,
//      'Imageguid': '',
//      'ModelNumber': '其它.款数',
//      'OtherAccessories': '其它.配件',
//      'OtherBannerCloth': '其它.旗帜布料',
//      'OtherHem': '其它.缝边',
//      'OtherLabel': '其它.标签',
//      'OtherPackaging': '其它.包装',
//      'OtherPantoneColorNo': '其它.潘通色号',
//      'OtherPhoto': '其它.拍照要求',
//      'OtherPrinting': '其它.印刷',
//      'OtherPurposeNumber': '其它.留底数量',
//      'OtherWaistSizeCloth': '其它.腰头尺寸布料',
//      'PackagingRemark': '其它.包装备注',
//      'Quantitydifference': 1,
//      'Remark': '',
//      'SandwichName': '其它.帐篷画面',
//      'SewingRemark': '其它.缝纫备注',
//      'SprayPrintingRemark': '其它.喷印备注',
//      'TotalpricePI': '其它.PI总价',
//      'UnitpricePI': '其它.PI单价',
//      'firstConfirmed': '首样确认',
//      'goodsEName': '',
//      'goodscDesc': '',
//      'goodscname': '',
//      'goodscode': '',
//      'goodsename': '',
//      'imgId': '58658',
//      'layerNumber': '其它.层数',
//      'otherFlagsSize': '其它.旗帜尺寸',
//      'otherProductType': '其它.产品细类',
//      'productRemark': '其它.备注',
//      'productSizes': [
//        {
//          'amount': 1,
//          'layerNumber': 1,
//          'modelNumber': 1,
//          'psName': 'A',
//          'sprayPrintWide': 1
//        }
//      ],
//      'serialNumber': '其它.产品序列号',
//      'sewingWaistHead': '其它.缝纫方式',
//      'sprayPrintingRemark': '其它.喷印备注',
//      'stockID': 1,
//      'stockNo': '其它.备注',
//      'totalprice': '',
//      'unitprice': ''
//    }
//  ],
//  'remark': '',
//  'signDate': 1507042285244,
//  'stockNo': '160520NC0223-NorthStar0523'
//}

