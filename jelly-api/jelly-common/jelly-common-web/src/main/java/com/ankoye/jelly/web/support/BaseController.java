package com.ankoye.jelly.web.support;

import com.ankoye.jelly.base.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author ankoye@qq.com
 */
public abstract class BaseController<T extends Serializable> {

    @Autowired
    private BaseService<T> service;

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Object id) {
        T entity = service.selectById(id);
        return Result.success(entity);
    }

    /**
     * 获取数据库所有的行 进行分页
     * 也就是说没有其他限定条件
     */
    @GetMapping("/all/{page}/{size}")
    public Result selectAllByRowBounds(@PathVariable Integer page, @PathVariable Integer size) {
        List<T> list = service.selectByRowBounds(null, page, size);
        return Result.success(list);
    }

    @GetMapping("/list/{page}/{size}")
    public Result selectListByRowBounds(@RequestBody T record, @PathVariable Integer page, @PathVariable Integer size) {
        List<T> list = service.selectByRowBounds(record, page, size);
        return Result.success(list);
    }

    @GetMapping("/all")
    public Result selectAll() {
        List<T> list = service.selectAll();
        return Result.success(list);
    }

    @PostMapping("/save")
    public Result save(T record) {
        int res = service.save(record);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateById(T record) {
        int res = service.updateById(record);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Object id) {
        int res = service.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@PathVariable T record) {
        int res = service.delete(record);
        return Result.success();
    }
}
