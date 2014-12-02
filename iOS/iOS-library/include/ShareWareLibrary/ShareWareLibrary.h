//
//  ShareWareLibrary.h
//  ShareWareLibrary
//
//

#import <Foundation/Foundation.h>
#import "ShareWareArtifact.h"
#import <UIKit/UIKit.h>

/**
 * Delegate
 */
@protocol ShareWareLibraryDelegate

@optional
- (void) updatePermission : (NSString *) key : (NSString *) value : (NSString *) sharedWith;
@end

@interface ShareWareLibrary : NSObject {
}

@property (nonatomic, assign, getter=isFree)    BOOL                              isFree;
@property (strong, nonatomic)                   NSMutableArray                    *artifactsArray;
@property (assign, nonatomic)                   id <ShareWareLibraryDelegate>     sDelegate;


- (void) launchUI : (UIViewController *) publisher;

@end
