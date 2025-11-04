package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoSetConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定義切面，實現公共字段自動填充的處理邏輯
 * 公共字段包含：
 *      createTime、updateTime、createUser、updateUser
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入點
     * @annotation 是為了標記哪些"連接點"需要被使用。
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFilPointCut(){}

    /**
     * 前置通知，在通知中進行公共字段的賦值
     */
    @Before("autoFilPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("開始進行公共字段的自動填充 . . .");

        // 獲取到當前被攔截的方法上的數據庫操作類型
        // 此區域JoinPoint的API
        MethodSignature  signature = (MethodSignature) joinPoint.getSignature();// 獲得方法簽名對象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);// 獲得方法上的註解對象
        OperationType operationType = autoFill.value(); // 獲得數據庫操作類型

        // 獲取目標方法參數
        Object[] args = joinPoint.getArgs();

        // 防止空指針
        if(args == null || args.length == 0) {
            return;
        }

        Object entity = args[0]; // 約定：mapper中insert/update傳參都只會有一個對象

        // 準備賦值的數據
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 根據當前不同的操作類型，為對應的屬性通過反射來賦值
        if(operationType == OperationType.INSERT) {
            // 賦值 4 個公共字段
            try {
                // 此區域是使用了反射API
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoSetConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoSetConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoSetConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoSetConstant.SET_UPDATE_USER, Long.class);

                // 通過反射為對象屬性賦值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if(operationType == OperationType.UPDATE) {
            // 賦值 2 個公共字段
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoSetConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoSetConstant.SET_UPDATE_USER, Long.class);

                // 通過反射為對象屬性賦值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
