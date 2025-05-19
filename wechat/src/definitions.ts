export enum Scene {
  SESSION = 0,
  TIMELINE = 1,
  FAVORITE = 2,
}
export enum MiniprogramType {
  RELEASE = 0,
  TEST = 1,
  PREVIEW = 2,
}

interface WXMediaMessage {
  title: string;
  description: string;
}
interface SendMessageToWX {
  scene: Scene;
}
interface ShareImage extends WXMediaMessage, SendMessageToWX {
  imagePath: string;
  thumbPath: string;
  entranceMiniProgramUsername: string;
  entranceMiniProgramPath: string;
}
interface ShareText extends WXMediaMessage, SendMessageToWX {
  text: string;
}
interface ShareMiniProgram extends WXMediaMessage, SendMessageToWX {
  webpageUrl: string;
  userName: string;
  path: string;
  hdImageData: string;
  withShareTicket: boolean;
  miniprogramType: MiniprogramType;
}
interface ShareWeb extends WXMediaMessage, SendMessageToWX {
  webpageUrl: string;
}
interface ShareVideo extends WXMediaMessage, SendMessageToWX {
  videoUrl: string;
  videoLowBandUrl: string;
}
interface ShareMusicVideo extends WXMediaMessage, SendMessageToWX {
  musicUrl: string;
  musicDataUrl: string;
  singerName: string;
  duration: number;
  songLyric: string;
  hdAlbumThumbFilePath: string;
  albumName: string;
  musicGenre: string;
  issueDate: number;
  identification: string;
  hdAlbumThumbFileHash: string;
}

export interface WechatPlugin {
  isInstalled(): Promise<{ data: boolean }>;
  registerApp(): Promise<{ data: boolean }>;

  shareImageMessage(options: ShareImage): Promise<{ data: boolean }>;
  shareMiniProgramMessage(options: ShareMiniProgram): Promise<{ data: boolean }>;
  shareTextMessage(options: ShareText): Promise<{ data: boolean }>;
  shareWebPageMessage(options: ShareWeb): Promise<{ data: boolean }>;
  shareVideoMessage(options: ShareVideo): Promise<{ data: boolean }>;
  shareMusicVideoMessage(options: ShareMusicVideo): Promise<{ data: boolean }>;

  auth(options: { scope: string; state: string }): Promise<{ data: { appid: string; scope: string; state: string } }>;
  sendPaymentRequest(options: object): Promise<{ data: boolean }>;
  openMiniProgram(options: { userName: string; path: string; miniprogramType: string }): Promise<{ data: boolean }>;

  openCustomerServiceResp(options: {
    corpId: string;
    url: string;
  }): Promise<{ data: { errCode: number; extMsg: string } }>;
}
