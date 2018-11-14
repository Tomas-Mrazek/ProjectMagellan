package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Effect {

    @NotNull
    CommandType command;
    @NotNull
    String version;
    int duration;
    String animName;
    String newName;
    @NotNull
    EffectType animType;
    String animData;
    ColorType colorType;
    List<PaletteColor> palette;
    BrightnessRange brightnessRange;
    TransitionTime transTime;
    DelayTimeRange delayTime;
    @Size(min = 0)
    float flowFactor;
    @Size(min = 0, max = 1)
    float explodeFactor;
    int windowSize;
    Direction direction;
    @NotNull
    boolean loop;

}
