package jpabook.jpashop.service;

import jpabook.jpashop.domain.entity.item.Book;
import jpabook.jpashop.domain.entity.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ItemServiceTest {

    @Autowired private ItemRepository itemRepository;
    @Autowired private ItemService itemService;

    @Test
    public void 아이템_저장() throws Exception {
        //given
        Item item = new Book();

        //when
        itemService.saveItem(item);

        //then
        Assertions.assertThat(itemService.findOne(item.getId())).isEqualTo(item);
    }

    @Test
    public void 아이템_조회() throws Exception {
        //given
        Item item = new Book();

        //when
        itemService.saveItem(item);

        //then
        Assertions.assertThat(itemService.findOne(item.getId())).isEqualTo(item);

    }
}