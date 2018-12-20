package cz.jaktoviditoka.projectmagellan.razer.chroma.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class EffectNoneRequest extends EffectRequest {

    EffectType effect = EffectType.CHROMA_NONE;

}
