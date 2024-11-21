# @yinxianwei/wechat

capacitor wechat

## Install

```bash
npm install @yinxianwei/wechat
npx cap sync
```

## API

<docgen-index>

* [`isInstalled()`](#isinstalled)
* [`registerApp()`](#registerapp)
* [`shareImageMessage(...)`](#shareimagemessage)
* [`shareMiniProgramMessage(...)`](#shareminiprogrammessage)
* [`shareTextMessage(...)`](#sharetextmessage)
* [`shareWebPageMessage(...)`](#sharewebpagemessage)
* [`shareVideoMessage(...)`](#sharevideomessage)
* [`shareMusicVideoMessage(...)`](#sharemusicvideomessage)
* [`auth(...)`](#auth)
* [`sendPaymentRequest(...)`](#sendpaymentrequest)
* [`openMiniProgram(...)`](#openminiprogram)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isInstalled()

```typescript
isInstalled() => Promise<{ data: boolean; }>
```

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### registerApp()

```typescript
registerApp() => Promise<{ data: boolean; }>
```

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### shareImageMessage(...)

```typescript
shareImageMessage(options: ShareImage) => Promise<{ data: boolean; }>
```

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#shareimage">ShareImage</a></code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### shareMiniProgramMessage(...)

```typescript
shareMiniProgramMessage(options: ShareMiniProgram) => Promise<{ data: boolean; }>
```

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#shareminiprogram">ShareMiniProgram</a></code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### shareTextMessage(...)

```typescript
shareTextMessage(options: ShareText) => Promise<{ data: boolean; }>
```

| Param         | Type                                            |
| ------------- | ----------------------------------------------- |
| **`options`** | <code><a href="#sharetext">ShareText</a></code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### shareWebPageMessage(...)

```typescript
shareWebPageMessage(options: ShareWeb) => Promise<{ data: boolean; }>
```

| Param         | Type                                          |
| ------------- | --------------------------------------------- |
| **`options`** | <code><a href="#shareweb">ShareWeb</a></code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### shareVideoMessage(...)

```typescript
shareVideoMessage(options: ShareVideo) => Promise<{ data: boolean; }>
```

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#sharevideo">ShareVideo</a></code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### shareMusicVideoMessage(...)

```typescript
shareMusicVideoMessage(options: ShareMusicVideo) => Promise<{ data: boolean; }>
```

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#sharemusicvideo">ShareMusicVideo</a></code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### auth(...)

```typescript
auth(options: { scope: string; state: string; }) => Promise<{ data: { appid: string; scope: string; state: string; }; }>
```

| Param         | Type                                           |
| ------------- | ---------------------------------------------- |
| **`options`** | <code>{ scope: string; state: string; }</code> |

**Returns:** <code>Promise&lt;{ data: { appid: string; scope: string; state: string; }; }&gt;</code>

--------------------


### sendPaymentRequest(...)

```typescript
sendPaymentRequest(options: object) => Promise<{ data: boolean; }>
```

| Param         | Type                |
| ------------- | ------------------- |
| **`options`** | <code>object</code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### openMiniProgram(...)

```typescript
openMiniProgram(options: { userName: string; path: string; miniprogramType: string; }) => Promise<{ data: boolean; }>
```

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code>{ userName: string; path: string; miniprogramType: string; }</code> |

**Returns:** <code>Promise&lt;{ data: boolean; }&gt;</code>

--------------------


### Interfaces


#### ShareImage

| Prop                              | Type                |
| --------------------------------- | ------------------- |
| **`imagePath`**                   | <code>string</code> |
| **`thumbPath`**                   | <code>string</code> |
| **`entranceMiniProgramUsername`** | <code>string</code> |
| **`entranceMiniProgramPath`**     | <code>string</code> |


#### ShareMiniProgram

| Prop                  | Type                                                        |
| --------------------- | ----------------------------------------------------------- |
| **`webpageUrl`**      | <code>string</code>                                         |
| **`userName`**        | <code>string</code>                                         |
| **`path`**            | <code>string</code>                                         |
| **`hdImageData`**     | <code>string</code>                                         |
| **`withShareTicket`** | <code>boolean</code>                                        |
| **`miniprogramType`** | <code><a href="#miniprogramtype">MiniprogramType</a></code> |


#### ShareText

| Prop       | Type                |
| ---------- | ------------------- |
| **`text`** | <code>string</code> |


#### ShareWeb

| Prop             | Type                |
| ---------------- | ------------------- |
| **`webpageUrl`** | <code>string</code> |


#### ShareVideo

| Prop                  | Type                |
| --------------------- | ------------------- |
| **`videoUrl`**        | <code>string</code> |
| **`videoLowBandUrl`** | <code>string</code> |


#### ShareMusicVideo

| Prop                       | Type                |
| -------------------------- | ------------------- |
| **`musicUrl`**             | <code>string</code> |
| **`musicDataUrl`**         | <code>string</code> |
| **`singerName`**           | <code>string</code> |
| **`duration`**             | <code>number</code> |
| **`songLyric`**            | <code>string</code> |
| **`hdAlbumThumbFilePath`** | <code>string</code> |
| **`albumName`**            | <code>string</code> |
| **`musicGenre`**           | <code>string</code> |
| **`issueDate`**            | <code>number</code> |
| **`identification`**       | <code>string</code> |
| **`hdAlbumThumbFileHash`** | <code>string</code> |


### Enums


#### MiniprogramType

| Members       | Value          |
| ------------- | -------------- |
| **`RELEASE`** | <code>0</code> |
| **`TEST`**    | <code>1</code> |
| **`PREVIEW`** | <code>2</code> |

</docgen-api>
