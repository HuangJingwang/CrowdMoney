package com.example.service.Impl;

import com.example.entity.Menu;
import com.example.entity.MenuExample;
import com.example.mapper.MenuMapper;
import com.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
   private MenuMapper menuMapper;
    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }
    // service 代码
    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }
    // service 代码
    @Override
    public void updateMenu(Menu menu) {
// 由于 pid 没有传入，一定要使用有选择的更新，保证“pid”字段不会被置空
        menuMapper.updateByPrimaryKeySelective(menu);
    }
    // service 代码
    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
