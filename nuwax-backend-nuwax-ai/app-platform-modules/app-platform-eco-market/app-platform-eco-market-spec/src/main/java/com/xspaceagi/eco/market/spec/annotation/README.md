# 管理员权限注解使用说明

## 概述

`@RequireAdmin` 注解用于标记需要管理员权限才能访问的接口方法。当用户访问带有此注解的方法时，系统会自动验证当前用户是否具有管理员权限。

## 使用方法

### 基本用法

```java
@RequireAdmin
@PostMapping("/admin/save")
public ReqResult<Boolean> saveConfig(@RequestBody ConfigDto configDto) {
    // 方法实现
}
```

### 自定义权限描述

```java
@RequireAdmin("只有管理员才能删除配置")
@DeleteMapping("/admin/delete/{id}")
public ReqResult<Boolean> deleteConfig(@PathVariable Long id) {
    // 方法实现
}
```

### 跳过权限检查（测试环境）

```java
@RequireAdmin(value = "管理员权限", skipCheck = true)
@PostMapping("/admin/test")
public ReqResult<String> testMethod() {
    // 在测试环境中跳过权限检查
}
```

## 工作原理

1. **AOP切面拦截**：`AdminPermissionAspect` 切面会拦截所有带有 `@RequireAdmin` 注解的方法
2. **用户身份验证**：从 `RequestContext` 中获取当前登录用户信息
3. **权限验证**：调用 `UserApplicationService` 验证用户是否具有管理员角色
4. **异常处理**：如果权限验证失败，抛出 `AdminPermissionException`
5. **全局异常处理**：`GlobalExceptionHandler` 捕获权限异常并返回统一的错误响应

## 异常处理

当权限验证失败时，系统会抛出 `AdminPermissionException`，并通过全局异常处理器返回 HTTP 403 状态码和错误信息。

## 注意事项

1. 确保用户已登录，否则会抛出"用户未登录"异常
2. 只有角色为 `User.Role.Admin` 的用户才能通过权限验证
3. 建议在每个需要管理员权限的方法上都添加有意义的权限描述信息
4. `skipCheck` 参数仅用于特殊场景，生产环境中应谨慎使用

## 相关类

- `@RequireAdmin`：权限注解
- `AdminPermissionAspect`：权限验证切面
- `AdminPermissionException`：权限异常
- `GlobalExceptionHandler`：全局异常处理器