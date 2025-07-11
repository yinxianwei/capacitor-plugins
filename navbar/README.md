# @yinxianwei/navbar

capacitor navbar

## Install

```bash
npm install @yinxianwei/navbar
npx cap sync
```

## API

<docgen-index>

* [`setup(...)`](#setup)
* [`setTitle(...)`](#settitle)
* [`setLeftIcon(...)`](#setlefticon)
* [`setLeftVisibility(...)`](#setleftvisibility)
* [`setRightIcon(...)`](#setrighticon)
* [`setRightVisibility(...)`](#setrightvisibility)
* [`allowsBackForwardNavigationGestures(...)`](#allowsbackforwardnavigationgestures)
* [`exitApp()`](#exitapp)
* [`setVisibility(...)`](#setvisibility)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### setup(...)

```typescript
setup(options: { title: string; leftIcon: string; leftVisibility: boolean; rightIcon: string; rightVisibility: false; }) => Promise<boolean>
```

| Param         | Type                                                                                                                  |
| ------------- | --------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ title: string; leftIcon: string; leftVisibility: boolean; rightIcon: string; rightVisibility: false; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### setTitle(...)

```typescript
setTitle(options: { value: string; }) => Promise<boolean>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### setLeftIcon(...)

```typescript
setLeftIcon(options: { normal: string; }) => Promise<boolean>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ normal: string; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### setLeftVisibility(...)

```typescript
setLeftVisibility(options: { value: boolean; }) => Promise<boolean>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ value: boolean; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### setRightIcon(...)

```typescript
setRightIcon(options: { normal: string; }) => Promise<boolean>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ normal: string; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### setRightVisibility(...)

```typescript
setRightVisibility(options: { value: boolean; }) => Promise<boolean>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ value: boolean; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### allowsBackForwardNavigationGestures(...)

```typescript
allowsBackForwardNavigationGestures(options: { value: boolean; }) => Promise<boolean>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ value: boolean; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### exitApp()

```typescript
exitApp() => Promise<boolean>
```

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------


### setVisibility(...)

```typescript
setVisibility(options: { value: boolean; }) => Promise<boolean>
```

| Param         | Type                             |
| ------------- | -------------------------------- |
| **`options`** | <code>{ value: boolean; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

--------------------

</docgen-api>
