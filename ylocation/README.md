# @yinxianwei/ylocation

ylocation

## Install

```bash
npm install @yinxianwei/ylocation
npx cap sync
```

## API

<docgen-index>

* [`requestPermissions()`](#requestpermissions)
* [`getCurrentPosition(...)`](#getcurrentposition)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### requestPermissions()

```typescript
requestPermissions() => Promise<boolean>
```

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### getCurrentPosition(...)

```typescript
getCurrentPosition(options: { enableHighAccuracy: boolean; timeout: number; }) => Promise<Position>
```

| Param         | Type                                                           |
| ------------- | -------------------------------------------------------------- |
| **`options`** | <code>{ enableHighAccuracy: boolean; timeout: number; }</code> |

**Returns:** <code>Promise&lt;<a href="#position">Position</a>&gt;</code>

--------------------


### Interfaces


#### Position

| Prop            | Type                                                                                                                                                                                | Description                                             | Since |
| --------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------- | ----- |
| **`timestamp`** | <code>number</code>                                                                                                                                                                 | Creation timestamp for coords                           | 1.0.0 |
| **`coords`**    | <code>{ latitude: number; longitude: number; accuracy: number; altitudeAccuracy: number \| null; altitude: number \| null; speed: number \| null; heading: number \| null; }</code> | The GPS coordinates along with the accuracy of the data | 1.0.0 |

</docgen-api>
