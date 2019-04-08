//package com.mars.yoyo.hotspot.common.dps.log;
//
//import com.google.common.base.Joiner;
//import com.google.common.base.Objects;
//import com.tuodao.bp.api.core.constant.FrameConstant;
//import com.tuodao.bp.api.core.constant.RequestConstant;
//import com.tuodao.bp.api.core.constant.ResponseConstant;
//import com.tuodao.bp.api.core.log.SystemOperateLog;
//import org.joda.time.DateTime;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import org.springframework.web.util.UrlPathHelper;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 请求日志拦截器
// * @author tookbra
// * @date 2018/6/4
// * @description
// */
//public class LogHandlerInterceptor extends HandlerInterceptorAdapter {
//
//
//	/** 用户操作日志组件 */
//	@Resource
//	private SystemOperateLogComponent userOperateLogComponent;
//
//	/**
//	 * 拦截请求进入前
//	 *
//	 * @param request
//	 *            请求信息
//	 * @param response
//	 *            返回信息
//	 * @param handler
//	 *            操作类
//	 * @return 是否拦截，true：拦截 / false：不拦截
//	 * @throws Exception
//	 */
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//
//		// 如果不符合拦截条件
//		if (!isHandled(handler)) {
//			return true;
//		}
//
//		// 记录操作日志
//		SystemOperateLog userOperateLog = userOperateLogComponent.currentUserOperateLog();
//		// 记录服务器时间
//		userOperateLog.setServerTime(DateTime.now().toDate());
//
//		UrlPathHelper urlPathHelper = new UrlPathHelper();
//		String path = urlPathHelper.getLookupPathForRequest(request);
//
//		// 请求接口
//		userOperateLog.setActionCode(path);
//
//		// 记录下接口请求处理开始时间
//		long startTime = System.currentTimeMillis();
//		request.setAttribute(RequestConstant.REQUEST_HEADER_START_TIME, startTime);
//		userOperateLog.setRequestTime(DateTime.now().toDate());
//
//		// 记录请求地址
//		userOperateLog.setRequestUrl(request.getRequestURL().toString());
//
//		return true;
//	}
//
//	/**
//	 * 请求返回之前
//	 *
//	 * @param request
//	 *            请求信息
//	 * @param response
//	 *            请求返回信息
//	 * @param handler
//	 *            操作类
//	 * @param ex
//	 *            异常
//	 * @throws Exception
//	 */
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//
//		// 如果不符合拦截条件
//		if (!isHandled(handler)) {
//			return;
//		}
//
//		// 记录操作日志
//		SystemOperateLog userOperateLog = userOperateLogComponent.currentUserOperateLog();
//		// 取得请求开始开始时间
//		long start = (long) request.getAttribute(RequestConstant.REQUEST_HEADER_START_TIME);
//		// 取得请求结束时间
//		long end = System.currentTimeMillis();
//		// 记录请求花费时间
//		userOperateLog.setRequestDuration((int) (end - start));
//		// 记录请求结束时间
//		userOperateLog.setCompleteTime(DateTime.now().toDate());
//
//		// 如果是下载文件
//		if (Objects.equal(ResponseConstant.OCTET_STREAM_CONTENT_TYPE, response.getContentType())) {
//
//			// 成功
//			userOperateLog.setStatus(0);
//
//			// 请求状态代码
//			userOperateLog.setCode(String.valueOf(FrameConstant.SUCCESS_CODE));
//
//			// 取得文件名
//			String fileName = response.getHeader("Content-Disposition").substring("attachment;filename=\"".length());
//
//			String remark = Joiner.on("").join("用户ID为[", userOperateLog.getUserId(), "]下载了一个文件,文件名为：", fileName);
//
//			// 设置备注
//			userOperateLog.setRemark(remark);
//		}
//
//		// 记录日志
//		userOperateLogComponent.recordLog();
//
//	}
//
//	/**
//	 * 判断是否要拦截
//	 *
//	 * @param handler
//	 *            控制器
//	 * @return 是否要拦截，true：拦截 / false：不拦截
//	 */
//	private Boolean isHandled(Object handler) {
//
//		// 增加支持spring普通方式访问
//		HandlerMethod handlerMethod = (HandlerMethod) handler;
//
//		MethodParameter parameter = handlerMethod.getMethodParameters()[0];
//
//		// 获取RestController注解
//		// 从该方法所属类中取得注解
//		RestController annotation = parameter.getDeclaringClass().getAnnotation(RestController.class);
//		if (annotation != null) {
//			return true;
//		}
//		// 从该方法中取得注解
//		annotation = parameter.getMethodAnnotation(RestController.class);
//		if (annotation != null) {
//			return true;
//		}
//		// 从该方法的参数中取得注解
//		annotation = parameter.getParameterAnnotation(RestController.class);
//		if (annotation != null) {
//			return true;
//		}
//
//		return false;
//	}
//
//}
