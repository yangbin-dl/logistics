package povo;

import com.mallfe.item.pojo.Pl;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-08-13
 */
@Data
public class PlVo {

    private String id;
    private String name;
    private int level;
    private List<PlVo> children = new ArrayList<>();

    public PlVo(){

    }
    public PlVo(Pl i) {
        this.id = i.getPlbm();
        this.name = i.getPlbm()+i.getPlmc();
        this.level = i.getLevel();
    }
}
