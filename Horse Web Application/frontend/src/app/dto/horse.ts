import { Sport } from "./sport";

export interface Horse {
    id: number;
    name: string;
    desc?: string;
    dob: Date;
    sex: String;
    favoriteSport?: Sport;
    dad?: Horse;
    mom?: Horse;

}

