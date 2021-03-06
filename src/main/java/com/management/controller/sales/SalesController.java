package com.management.controller.sales;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.management.common.Page;
import com.management.common.Result;
import com.management.common.util.DateUtils;
import com.management.model.FinanceStatistical;
import com.management.model.vo.DeviceVO;
import com.management.model.vo.GameVO;
import com.management.model.vo.SiteSaleVO;
import com.management.model.vo.SiteVO;
import com.management.service.operate.FinanceStatisticalService;
import com.management.service.sales.SaleService;

/**
 * 
 * @author <a href="mailto:sawyer@wolaidai.com">sawyer</a>
 * @date 2016年8月9日
 */
@Controller
@RequestMapping(value="/sales")
public class SalesController
{

	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private FinanceStatisticalService financeStatisticalService;
	
	@RequestMapping(value = "/device_sales")
	public String device(@RequestParam(value="account",defaultValue="",required=false)String account,
			Model model)
	{    
		model.addAttribute("account", account);
		return "sales/device_sales";
	}
	
	@RequestMapping(value = "/device_sales_list")
	public @ResponseBody Page<DeviceVO> deviceList (Page<DeviceVO> page,DeviceVO record)
	{
		return saleService.getDeviceSales(page, record);
	}
	
	
	@RequestMapping(value = "/device_sales_details")
	public String deviceSalesDetails(@RequestParam(value="deviceCode",defaultValue="",required=false)String deviceCode,Model model)
	{   
		model.addAttribute("deviceCode", deviceCode);
		return "sales/device_sales_details";
	}
	
	
	@RequestMapping(value = "/device_sales_details_list")
	public @ResponseBody Page<GameVO> deviceSalesDetails (Page<GameVO> page,GameVO record)
	{
		return saleService.getDeviceGamesSales(page, record);
	}
	
	
	@RequestMapping(value = "/site_sales")
	public String siteSales()
	{    
		return "sales/site_sales";
	}
	
	@RequestMapping(value = "/site_sales_list")
	public @ResponseBody Page<SiteVO> siteSalesList (Page<SiteVO> page,SiteVO record)
	{
		return saleService.getBySiteByAccountAndSiteName(page, record);
	}
	
	
	@RequestMapping(value = "/site_sales_details")
	public String siteSalesDetails(@RequestParam(value="account",defaultValue="",required=false)String account,Model model)
	{   
		model.addAttribute("account", account);
		return "sales/site_sales_details";
	}
	
	
	
	@RequestMapping(value = "/compare_bill")
	public String compareBill(
			Model model)
	{    
		return "sales/compare_bill";
	}
	
	@RequestMapping(value = "/compare_bill_list")
	public @ResponseBody Page<SiteSaleVO> compareBillList (Page<SiteSaleVO> page,SiteSaleVO siteSaleVO)
	{
		return saleService.getSitetGameSalesAmountByAccountAndReportDate(page, siteSaleVO);
	}
	
	
	@RequestMapping(value = "/comfirm_fanance")
	public @ResponseBody Result comfirmFanance (FinanceStatistical financeStatistical,
			@RequestParam(value="reportTime",defaultValue="",required=false)String reportTime)
	{
		if (financeStatisticalService.getFinanceStatisticalByUserAndDate(reportTime, financeStatistical.getUserId())!=null)
		{
			return Result.failed("该记录已确认！");
		}
		
		if (StringUtils.isNoneBlank(reportTime))
		{
			financeStatistical.setBillDate(DateUtils.parseDate(reportTime));
		}
		return financeStatisticalService.creatFinanceStatistical(financeStatistical);
	}
	
}
