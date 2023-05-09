export interface UserGeneratedUrlModel {
  id: number;
  urlName: string;
  longUrl: string;
  tinyUrl: string;
  shortenedBitlyUrl: string;
  userEmail: string;
  customUrl: string;
  expiryDate: string;
  noOfClicked: number;
  createdDate: string;
  isCustomRequested: boolean;
}
