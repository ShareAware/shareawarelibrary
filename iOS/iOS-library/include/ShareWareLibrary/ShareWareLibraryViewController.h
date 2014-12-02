//
//  ShareWareLibraryViewController.h
//  ShareWareLibraryApp
//
//  Created by Paddy Vishnubhatt on 1/3/14.
//  Copyright (c) 2014 Paddy Vishnubhatt. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import "QServer.h"

static NSString * kSharewareBonjourType = @"_sharewarepeer._tcp.";

#ifndef __SHARE__

@protocol ShareWareLibraryDelegate

@optional
- (void) updatePermission : (NSString *) key : (NSString *) value : (NSString *) sharedWith;
@end

@interface ShareWareArtifact : NSObject {
    NSString *name;
    NSString *permission;
    NSString *sharedWith;
}
@property (strong, nonatomic) NSString *name;
@property (strong, nonatomic) NSString *permission;
@property (strong, nonatomic) NSString *sharedWith;
@end

#define __SHARE__

#endif

@interface ShareWareLibraryViewController : UIViewController <UITableViewDelegate, UITableViewDataSource> {
    QServer *qServer;
    NSMutableString *passwordSent;
    NSMutableArray *artifactsArray;
    BOOL isFree;
     __unsafe_unretained id <ShareWareLibraryDelegate> _delegate;
}
@property (strong, nonatomic) IBOutlet UISegmentedControl *peerSegControl;
- (IBAction)peerSegControlHandler:(id)sender;
@property (assign, nonatomic) id <ShareWareLibraryDelegate> delegate;
@property (strong, nonatomic) IBOutlet UILabel *peerFoundLabel;
@property (strong, nonatomic) IBOutlet UIButton *connectButton;
- (IBAction)connectButtonHandler:(id)sender;
@property (strong, nonatomic) IBOutlet UILabel *statusLabel;
@property (nonatomic, assign, getter=isFree) BOOL isFree;
@property (strong, nonatomic) NSMutableArray *artifactsArray;
@property (strong, nonatomic) IBOutlet UITableView *artifactsTableView;

@end
